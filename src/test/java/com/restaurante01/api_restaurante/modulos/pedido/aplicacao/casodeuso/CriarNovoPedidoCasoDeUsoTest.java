package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;
import com.restaurante01.api_restaurante.builders.CardapioProdutoBuilder;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.ClienteBuilder;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.*;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoAssociacaoPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoClientePorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.*;
import org.instancio
        .Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarNovoPedidoCasoDeUsoTest {

    @Mock private PedidoRepositorio pedidoRepository;
    @Mock private PedidoMapeador pedidoMapeador;
    @Mock private PedidoAssociacaoPorta pedidoAssociacaoPorta;
    @Mock private PedidoClientePorta pedidoClientePorta;
    @Mock private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CriarNovoPedidoCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve criar um pedido com sucesso quando os dados forem válidos")
    void deveCriarPedidoComStatusPendenteESalvarQuandoDadosValidos() {
        Associacao associacao = CardapioProdutoBuilder.umCardapioProduto().build();
        Cliente cliente = ClienteBuilder.umCliente().build();

        RepresentacaoProdutoItemPedido representacaoProduto = new RepresentacaoProdutoItemPedido(
                associacao.getProduto().getId(),
                associacao.getProduto().getNome());
        ProdutoVendido produtoVendido = new ProdutoVendido(
                representacaoProduto,
                associacao.resolverPrecoDeVenda());
        InformacoesClienteParaPedido infoCliente = new InformacoesClienteParaPedido(
                cliente.getId(), cliente.getNome(), cliente.getCpf().cpf(), cliente.getTelefone());
        EnderecoPedido enderecoPedidoCliente = new EnderecoPedido(
                cliente.getEnderecoCliente().rua(), cliente.getEnderecoCliente().numero(), cliente.getEnderecoCliente().bairro(),
                cliente.getEnderecoCliente().cidade(), cliente.getEnderecoCliente().estado(), cliente.getEnderecoCliente().cep(), cliente.getEnderecoCliente().referencia());

        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(associacao.getProduto().getId(), 2, "Teste de observação"));
        PedidoCriacaoDTO pedidoCriacaoDTO = new PedidoCriacaoDTO(
                associacao.getCardapio().getId(), itensDTO, null);
        PedidoDTO pedidoDTO = Instancio.create(PedidoDTO.class);

        when(pedidoMapeador.mapearParaValidacaoDeEstoque(itensDTO))
                .thenReturn(List.of(new ItemValidacaoEstoque(associacao.getProduto().getId(), 2)));
        when(pedidoClientePorta.obterDetalhesClienteParaPedido(cliente)).thenReturn(infoCliente);
        when(pedidoClientePorta.obterEndereco(cliente)).thenReturn(enderecoPedidoCliente);
        when(pedidoAssociacaoPorta.obterProdutoVendido(
                pedidoCriacaoDTO.idCardapio(),
                itensDTO.get(0).idProduto()))
                .thenReturn(produtoVendido);
        when(pedidoMapeador.mapearPedidoDto(any(Pedido.class))).thenReturn(pedidoDTO);

        ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
        ArgumentCaptor<PedidoCriadoEvento> eventoCaptor = ArgumentCaptor.forClass(PedidoCriadoEvento.class);

        PedidoDTO resultado = casoDeUso.executar(pedidoCriacaoDTO, cliente);

        verify(pedidoAssociacaoPorta).validarEstoque(eq(pedidoCriacaoDTO.idCardapio()), anyList());
        verify(pedidoAssociacaoPorta).obterProdutoVendido(
                pedidoCriacaoDTO.idCardapio(), itensDTO.get(0).idProduto());
        verify(pedidoClientePorta).obterDetalhesClienteParaPedido(cliente);
        verify(pedidoClientePorta).obterEndereco(cliente);
        verify(pedidoRepository).salvar(pedidoCaptor.capture());
        verify(eventPublisher).publishEvent(eventoCaptor.capture());

        Pedido pedidoSalvo = pedidoCaptor.getValue();
        PedidoCriadoEvento evento = eventoCaptor.getValue();

        assertThat(pedidoSalvo.getCliente()).isEqualTo(infoCliente);
        assertThat(pedidoSalvo.getStatusPedido()).isEqualTo(StatusPedido.PENDENTE);
        assertThat(pedidoSalvo.getItens()).hasSize(1);

        ItemPedido itemSalvo = pedidoSalvo.getItens().get(0);
        assertThat(itemSalvo.getProduto()).isEqualTo(representacaoProduto);
        assertThat(itemSalvo.getQuantidade()).isEqualTo(2);
        assertThat(itemSalvo.getPrecoUnitario()).isEqualByComparingTo(associacao.resolverPrecoDeVenda());
        assertThat(itemSalvo.getObservacao()).isEqualTo("Teste de observação");
        assertThat(evento.pedido()).isEqualTo(pedidoSalvo);
        assertThat(evento.pedido().getIdCardapio()).isEqualTo(pedidoCriacaoDTO.idCardapio());
        assertThat(resultado).isEqualTo(pedidoDTO);
    }

    @Test
    @DisplayName("Não deve salvar pedido nem publicar evento quando a validação de estoque falhar")
    void naoDeveSalvarQuandoValidacaoFalhar() {
        Cliente cliente = ClienteBuilder.umCliente().build();
        Associacao produtoSolicitado = CardapioProdutoBuilder.umCardapioProduto()
                .comQuantidadeCustomizada(3)
                .build();
        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(produtoSolicitado.getProduto().getId(), 10, "Teste"));
        PedidoCriacaoDTO pedidoCriacaoDTO = new PedidoCriacaoDTO(
                produtoSolicitado.getCardapio().getId(), itensDTO, null);

        when(pedidoMapeador.mapearParaValidacaoDeEstoque(itensDTO))
                .thenReturn(List.of(new ItemValidacaoEstoque(
                        produtoSolicitado.getProduto().getId(), 10)));
        doThrow(new RuntimeException("qualquer erro"))
                .when(pedidoAssociacaoPorta).validarEstoque(anyLong(), anyList());

        assertThatThrownBy(() -> casoDeUso.executar(pedidoCriacaoDTO, cliente))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("qualquer erro");

        verify(pedidoRepository, never()).salvar(any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}