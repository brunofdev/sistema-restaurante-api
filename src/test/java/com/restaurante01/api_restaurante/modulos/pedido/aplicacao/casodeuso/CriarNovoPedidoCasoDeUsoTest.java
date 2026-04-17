package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedidoBuilder;
import com.restaurante01.api_restaurante.builders.CardapioProdutoBuilder;
import com.restaurante01.api_restaurante.builders.ClienteBuilder;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ObterProdutoValorCostumizadoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ValidarEstoquePedidoUseCase;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador.EnderecoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
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

    @Mock
    private PedidoRepositorio pedidoRepository;
    @Mock
    private PedidoMapper pedidoMapper;
    @Mock
    private ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso;
    @Mock
    private ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private EnderecoMapper enderecoMapper;

    @InjectMocks
    private CriarNovoPedidoCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve criar um pedido com sucesso quando os dados forem válidos")
    void deveCriarPedidoComStatusPendenteESalvarQuandoDadosValidos() {
        CardapioProduto cardapioProduto = CardapioProdutoBuilder.umCardapioProduto().build();
        Cliente cliente = ClienteBuilder.umCliente().build();
        List<ItemPedidoSolicitadoDTO> itemPedidoSolicitadoDTOS = List.of(
                new ItemPedidoSolicitadoDTO(
                        cardapioProduto.getProduto().getId(),
                        2,
                        "Teste de observação"
                ));
        PedidoCriacaoDTO pedidoCriacaoDTO = new PedidoCriacaoDTO(cardapioProduto.getCardapio().getId(), itemPedidoSolicitadoDTOS, null);
        PedidoDTO pedidoDTO = Instancio.create(PedidoDTO.class);


        when(enderecoMapper.paraEndereco(null)).thenReturn(null);
        when(validarEstoquePedidoUseCase.executar(pedidoCriacaoDTO)).thenReturn(List.of(cardapioProduto));
        when(obterProdutoValorCostumizadoCasoDeUso.executar(pedidoCriacaoDTO.idCardapio(), pedidoCriacaoDTO.itens().get(0).idProduto())).thenReturn(cardapioProduto);
        when(pedidoMapper.mapearPedidoDto(any(Pedido.class))).thenReturn(pedidoDTO);

        ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
        ArgumentCaptor<PedidoCriadoEvento> eventoCaptor = ArgumentCaptor.forClass(PedidoCriadoEvento.class);

        PedidoDTO resultado = casoDeUso.executar(pedidoCriacaoDTO, cliente);


        verify(validarEstoquePedidoUseCase).executar(pedidoCriacaoDTO);
        verify(obterProdutoValorCostumizadoCasoDeUso).executar(pedidoCriacaoDTO.idCardapio(), itemPedidoSolicitadoDTOS.get(0).idProduto());
        verify(pedidoRepository).salvar(pedidoCaptor.capture());
        verify(eventPublisher).publishEvent(eventoCaptor.capture());

        Pedido pedidoSalvo = pedidoCaptor.getValue();
        PedidoCriadoEvento evento = eventoCaptor.getValue();

        assertThat(pedidoSalvo.getCliente()).isEqualTo(cliente);
        assertThat(pedidoSalvo.getStatusPedido()).isEqualTo(StatusPedido.PENDENTE);

        assertThat(pedidoSalvo.getItens()).hasSize(1);

        ItemPedido itemSalvo = pedidoSalvo.getItens().get(0);
        assertThat(itemSalvo.getProduto()).isEqualTo(cardapioProduto.getProduto());
        assertThat(itemSalvo.getQuantidade()).isEqualTo(2);
        assertThat(itemSalvo.getPrecoUnitario()).isEqualByComparingTo(cardapioProduto.resolverPrecoDeVenda());
        assertThat(itemSalvo.getObservacao()).isEqualTo("Teste de observação");
        assertThat(evento.pedido()).isEqualTo(pedidoSalvo);
        assertThat(evento.pedido().getIdCardapio()).isEqualTo(pedidoCriacaoDTO.idCardapio());
        assertThat(resultado).isEqualTo(pedidoDTO);
    }

    @Test
    @DisplayName("Não deve salvar pedido nem publicar evento quando a validação de estoque falhar")
    void naoDeveSalvarQuandoValidacaoFalhar() {
        Cliente cliente = ClienteBuilder.umCliente().build();
        CardapioProduto produtoSolicitado = CardapioProdutoBuilder.
                umCardapioProduto()
                .comQuantidadeCustomizada(3)
                .build();
        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(
                        produtoSolicitado.getProduto().getId(),
                        10,
                        "Teste"
                ));
        PedidoCriacaoDTO pedidoCriacaoDTO = new PedidoCriacaoDTO(
                produtoSolicitado.getCardapio().getId(),
                itensDTO,
                null)
                ;
        when(validarEstoquePedidoUseCase.executar(any()))
                .thenThrow(new RuntimeException("qualquer erro"));
        assertThatThrownBy(() -> casoDeUso.executar(pedidoCriacaoDTO, cliente));

        verify(pedidoRepository, never()).salvar(any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}