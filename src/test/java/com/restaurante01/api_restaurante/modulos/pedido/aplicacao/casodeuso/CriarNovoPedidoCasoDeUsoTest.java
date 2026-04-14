package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;
import com.restaurante01.api_restaurante.ItemPedidoBuilder;
import com.restaurante01.api_restaurante.builders.CardapioProdutoBuilder;
import com.restaurante01.api_restaurante.builders.ClienteBuilder;
import com.restaurante01.api_restaurante.builders.ProdutoBuilder;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ObterProdutoValorCostumizadoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ValidarEstoquePedidoUseCase;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.QntdCustomizadaInsuficienteException;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import org.instancio.Instancio;
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

    @InjectMocks
    private CriarNovoPedidoCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve criar um pedido com sucesso quando os dados forem válidos")
    void deveCriarPedidoComStatusPendenteESalvarQuandoDadosValidos() {
        CardapioProduto cardapioProduto = CardapioProdutoBuilder.umCardapioProduto().build();
        Cliente cliente = ClienteBuilder.umCliente().build();
        ItemPedido itemPedido = ItemPedidoBuilder.umItemPedido()
                .comProduto(cardapioProduto.getProduto())
                .comQuantidade(2)
                .comObservacao("Teste de observação")
                .build();
        List<ItemPedidoSolicitadoDTO> itemPedidoSolicitadoDTOS = List.of(
                new ItemPedidoSolicitadoDTO(
                        cardapioProduto.getProduto().getId(),
                        2,
                        "Teste de observação"
                ));
        PedidoCriacaoDTO pedidoCriacaoDTO = new PedidoCriacaoDTO(cardapioProduto.getCardapio().getId(), itemPedidoSolicitadoDTOS);
        PedidoDTO pedidoDTO = Instancio.create(PedidoDTO.class);

        when(validarEstoquePedidoUseCase.executar(pedidoCriacaoDTO)).thenReturn(List.of(cardapioProduto));
        when(obterProdutoValorCostumizadoCasoDeUso.executar(pedidoCriacaoDTO.idCardapio(), pedidoCriacaoDTO.itens().get(0).idProduto())).thenReturn(cardapioProduto);
        when(pedidoMapper.mapearItemPedido(itemPedido.getQuantidade(), cardapioProduto, itemPedido.getObservacao())).thenReturn(itemPedido);
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
        assertThat(pedidoSalvo.getItens()).contains(itemPedido);
        assertThat(pedidoSalvo.getItens()).hasSize(1);
        assertThat(evento.pedido()).isEqualTo(pedidoSalvo);
        assertThat(evento.idCardapio()).isEqualTo(pedidoCriacaoDTO.idCardapio());
        assertThat(resultado).isEqualTo(pedidoDTO);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não pertencer ao cardápio solicitado")
    void deveLancarAssociacaoNaoExisteExceptionQuandoProdutoNaoPertencerAoCardapio(){
        Cliente cliente = ClienteBuilder.umCliente().build();
        Produto produtoNaoAssociado = ProdutoBuilder.umProduto()
                .comId(2L)
                .comNome("X-SALADA")
                .build();
        CardapioProduto tabelaAssociativa = CardapioProdutoBuilder.umCardapioProduto().build();
        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(
                        produtoNaoAssociado.getId(),
                        1,
                        "Teste"
                ));
        PedidoCriacaoDTO pedidoCriacaoDTO = new PedidoCriacaoDTO(
                tabelaAssociativa.getCardapio().getId(),
                itensDTO)
                ;
        when(validarEstoquePedidoUseCase.executar(pedidoCriacaoDTO))
                .thenThrow(new AssociacaoNaoExisteException("Atenção: Um ou mais produtos solicitados não pertencem a este cardápio."));


        assertThatThrownBy(() -> casoDeUso.executar(pedidoCriacaoDTO, cliente))
                .isInstanceOf(AssociacaoNaoExisteException.class)
                .hasMessageContaining("Atenção: Um ou mais produtos solicitados não pertencem a este cardápio.");
        verify(pedidoRepository, never()).salvar(any());
        verify(eventPublisher, never()).publishEvent(any());

    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto solicitado não existir")
    public void deveLancarProdutoNaoEncontradoExceptionQuandoProdutoEnviadoNaoExistir(){
        Cliente cliente = ClienteBuilder.umCliente().build();
        CardapioProduto tabelaAssociativa = CardapioProdutoBuilder.umCardapioProduto().build();
        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(
                        999L,
                        1,
                        "Teste"
                ));
        PedidoCriacaoDTO pedidoCriacaoDTO = new PedidoCriacaoDTO(
                tabelaAssociativa.getCardapio().getId(),
                itensDTO)
                ;
        when(validarEstoquePedidoUseCase.executar(pedidoCriacaoDTO))
                .thenThrow(new ProdutoNaoEncontradoException("Produto não encontrado no estoque."));


        assertThatThrownBy(() -> casoDeUso.executar(pedidoCriacaoDTO, cliente))
                .isInstanceOf(ProdutoNaoEncontradoException.class)
                .hasMessageContaining("Produto não encontrado no estoque.");

        verify(pedidoRepository, never()).salvar(any());
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a quantidade solicitada exceder o estoque disponível")
    public void deveLancarQntdCustomizadaInsuficienteExceptionQuandoQntdCostumizadaForMenorQueSolicitada(){
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
                itensDTO)
                ;
        when(validarEstoquePedidoUseCase.executar(pedidoCriacaoDTO))
                .thenThrow(new QntdCustomizadaInsuficienteException("Quantidade indisponível para o produto: " + produtoSolicitado.getProduto().getNome()));


        assertThatThrownBy(() -> casoDeUso.executar(pedidoCriacaoDTO, cliente))
                .isInstanceOf(QntdCustomizadaInsuficienteException.class)
                .hasMessageContaining("Quantidade indisponível para o produto: " + produtoSolicitado.getProduto().getNome());

        verify(pedidoRepository, never()).salvar(any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}