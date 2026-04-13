package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ObterProdutoValorCostumizadoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ValidarEstoquePedidoUseCase;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import static org.assertj.core.api.Assertions.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @DisplayName("Cria novo pedido e retorno PedidoDTO completo")
    void deveRetornarDTO_quandoPedidoForCriado(){
        Pedido pedido = new Pedido();
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setUserName("joao123");
        cliente.setSenha("senha123");
        cliente.setEmail("joao@email.com");
        cliente.setCpf("12345678901");
        cliente.setRole(Role.USER);
        cliente.setContaAtiva(true);
        cliente.setTelefone("48999999999");
        cliente.setEstado("SC");
        cliente.setCidade("Florianópolis");
        cliente.setBairro("Centro");
        cliente.setCep("88000000");
        cliente.setRua("Rua Teste");
        cliente.setNumeroResidencia(100);

        Produto produto = new Produto(
                1L,
                "X-SALADA",
                "XIS COMPLETA SALADA COM MAIONESE",
                BigDecimal.valueOf(28.80),
                10L,
                true
        );
        Cardapio cardapio = new Cardapio(
                1L,
                "Cardapio de verão",
                "promoções de verão muito divertidas",
                true,
                LocalDate.now(),
                LocalDate.of(2026, 12, 28)
        );
        List<CardapioProduto> estoqueValidado = List.of(new CardapioProduto(
                1L,
                cardapio,
                produto,
                BigDecimal.valueOf(18.99),
                5,
                "Teste descricao customizada",
                true,
                "Observacao de teste"
        ));
        List<ItemPedidoSolicitadoDTO> itemPedidoSolicitadoDTO = List.of(
                new ItemPedidoSolicitadoDTO(1L, 2, "Sem maionese")
        );
        PedidoCriacaoDTO dto = new PedidoCriacaoDTO(1L,itemPedidoSolicitadoDTO);
        PedidoDTO pedidoDTO = new PedidoDTO(
                1L,
                "Bruno",
                "12312312312",
                "5199999999",
                List.of(
                        new ItemPedidoDTO("X-SALADA" , 2, BigDecimal.valueOf(18.99), "Sem maionese", BigDecimal.valueOf(18.99))
                ),
                BigDecimal.valueOf(18.99),
                StatusPedido.PENDENTE
        );
        CardapioProduto cardapioProduto = new CardapioProduto(
                1L,
                cardapio,
                produto,
                BigDecimal.valueOf(18.99),
                5,
                "Teste descricao customizada",
                true,
                "Observacao de teste"
        );
        ItemPedido itemPedido = new ItemPedido(
                1L,
                pedido,
                produto,
                2,
                BigDecimal.valueOf(18.99),
                "Observacao de Teste"
        );


        when(validarEstoquePedidoUseCase.executar(dto)).thenReturn(estoqueValidado);
        when(obterProdutoValorCostumizadoCasoDeUso.executar(dto.idCardapio(), produto.getId())).thenReturn(cardapioProduto);
        when(pedidoMapper.mapearItemPedido(2, cardapioProduto, "Sem maionese")).thenReturn(itemPedido);
        when(pedidoMapper.mapearPedidoDto(any())).thenReturn(pedidoDTO);


        PedidoDTO resultado = casoDeUso.executar(dto, cliente);

        assertThat(resultado).isEqualTo(pedidoDTO);

        verify(pedidoRepository).salvar(any());
        verify(eventPublisher).publishEvent(any());
    }

}