package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;
import com.restaurante01.api_restaurante.builders.CardapioProdutoBuilder;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoCupomPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoOutboxPorta;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.ClienteBuilder;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.*;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoAssociacaoPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoClientePorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.*;
import org.instancio
        .Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
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
    @Mock private PedidoCupomPorta pedidoCupomPorta;
    @Mock private PedidoOutboxPorta pedidoOutboxPorta;
    @Mock private ObjectMapper objectMapper;

    @InjectMocks
    private CriarNovoPedidoCasoDeUso casoDeUso;

    // fixtures compartilhadas
    private Associacao associacao;
    private Cliente cliente;
    private InformacoesClienteParaPedido infoCliente;
    private EnderecoPedido enderecoPedido;
    private ProdutoVendido produtoVendido;

    @BeforeEach
    void setUp() {
        associacao = CardapioProdutoBuilder.umCardapioProduto().build();
        cliente = ClienteBuilder.umCliente().build();
        infoCliente = new InformacoesClienteParaPedido(
                cliente.getId(), cliente.getNome(), cliente.getCpf().cpf(), cliente.getTelefone());
        enderecoPedido = new EnderecoPedido(
                cliente.getEnderecoCliente().rua(), cliente.getEnderecoCliente().numero(),
                cliente.getEnderecoCliente().bairro(), cliente.getEnderecoCliente().cidade(),
                cliente.getEnderecoCliente().estado(), cliente.getEnderecoCliente().cep(),
                cliente.getEnderecoCliente().referencia());
        produtoVendido = new ProdutoVendido(
                new RepresentacaoProdutoItemPedido(associacao.getProduto().getId(), associacao.getProduto().getNome()),
                associacao.resolverPrecoDeVenda());
    }

    // metodo auxiliar para o setup comum entre testes
    private void mockearSetupBasico(List<ItemPedidoSolicitadoDTO> itensDTO) {
        when(pedidoMapeador.mapearParaValidacaoDeEstoque(itensDTO))
                .thenReturn(List.of(new ItemValidacaoEstoque(associacao.getProduto().getId(), itensDTO.get(0).quantidade())));
        when(pedidoClientePorta.obterDetalhesClienteParaPedido(cliente)).thenReturn(infoCliente);
        when(pedidoClientePorta.obterEndereco(cliente)).thenReturn(enderecoPedido);
        when(pedidoAssociacaoPorta.obterProdutoVendido(any(), any())).thenReturn(produtoVendido);
        when(pedidoRepository.salvar(any())).thenAnswer(inv -> inv.getArgument(0));
        when(pedidoMapeador.mapearItemPedidoPayload(anyList())).thenReturn(List.of());
        when(pedidoMapeador.mapearPedidoCriadoDto(any(Pedido.class))).thenReturn(Instancio.create(PedidoCriadoDTO.class));
    }

    @Test
    @DisplayName("Deve criar um pedido com sucesso quando os dados forem válidos")
    void deveCriarPedidoComStatusPendenteESalvarQuandoDadosValidos() throws Exception {
        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(associacao.getProduto().getId(), 2, "obs"));
        PedidoCriacaoDTO dto = new PedidoCriacaoDTO(associacao.getCardapio().getId(), itensDTO, null, null);
        mockearSetupBasico(itensDTO);

        ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
        casoDeUso.executar(dto, cliente);
        verify(pedidoRepository).salvar(pedidoCaptor.capture());

        Pedido pedidoSalvo = pedidoCaptor.getValue();
        assertThat(pedidoSalvo.getCliente()).isEqualTo(infoCliente);
        assertThat(pedidoSalvo.getStatusPedido()).isEqualTo(StatusPedido.PENDENTE);
        assertThat(pedidoSalvo.getItens()).hasSize(1);
    }

    @Test
    @DisplayName("Deve aplicar desconto no pedido quando cupom válido for informado")
    void deveAplicarDescontoQuandoCupomValido() throws Exception {
        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(associacao.getProduto().getId(), 2, "obs"));
        PedidoCriacaoDTO dto = new PedidoCriacaoDTO(associacao.getCardapio().getId(), itensDTO, null, "DESCONTO10");
        CupomConsumido cupomMock = new CupomConsumido(1L, "DESCONTO10", new BigDecimal("10"), TipoDesconto.PORCENTAGEM, RegraRecorrencia.QUINZE_DIAS);

        mockearSetupBasico(itensDTO);
        when(pedidoCupomPorta.validarCupom(any())).thenReturn(cupomMock);


        ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
        casoDeUso.executar(dto, cliente);
        verify(pedidoRepository).salvar(pedidoCaptor.capture());

        Pedido pedidoSalvo = pedidoCaptor.getValue();
        assertThat(pedidoSalvo.getDescontoAplicado()).isGreaterThan(BigDecimal.ZERO);
        assertThat(pedidoSalvo.getValorTotal()).isLessThan(pedidoSalvo.getValorBruto());
        assertThat(pedidoSalvo.getCupom().codigoCupom()).isEqualTo("DESCONTO10");
    }

    @Test
    @DisplayName("Não deve salvar pedido nem publicar evento quando a validação de estoque falhar")
    void naoDeveSalvarQuandoValidacaoFalhar() {
        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(associacao.getProduto().getId(), 10, "obs"));
        PedidoCriacaoDTO dto = new PedidoCriacaoDTO(associacao.getCardapio().getId(), itensDTO, null, null);

        when(pedidoMapeador.mapearParaValidacaoDeEstoque(itensDTO))
                .thenReturn(List.of(new ItemValidacaoEstoque(associacao.getProduto().getId(), 10)));
        doThrow(new RuntimeException("qualquer erro"))
                .when(pedidoAssociacaoPorta).validarEstoque(anyLong(), anyList());

        assertThatThrownBy(() -> casoDeUso.executar(dto, cliente))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("qualquer erro");

        verify(pedidoRepository, never()).salvar(any());
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    @DisplayName("Não deve chamar porta de cupom quando nenhum cupom for informado")
    void naoDeveInteragirComCupomPortaQuandoCupomNaoInformado() throws Exception {
        List<ItemPedidoSolicitadoDTO> itensDTO = List.of(
                new ItemPedidoSolicitadoDTO(associacao.getProduto().getId(), 1, "obs"));
        PedidoCriacaoDTO dto = new PedidoCriacaoDTO(associacao.getCardapio().getId(), itensDTO, null, null);
        mockearSetupBasico(itensDTO);

        casoDeUso.executar(dto, cliente);

        verifyNoInteractions(pedidoCupomPorta);
    }
}

