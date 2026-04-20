package com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.EnderecoDoPedidoInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.StatusPedidoInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.EnderecoPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesClienteParaPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class PedidoTest {

    private InformacoesClienteParaPedido cliente;
    private EnderecoPedido enderecoPedidoValido;

    @BeforeEach
    void setUp() {
        cliente = new InformacoesClienteParaPedido(1L, "Roberto", "12312312332", "51515151");
        enderecoPedidoValido = new EnderecoPedido("Rua A", 100, "Centro",
                "Florianópolis", "Santa Catarina", "88000-000", null);
    }

    @Nested
    @DisplayName("criar()")
    class Criar {

        @Test
        @DisplayName("Deve criar pedido com status PENDENTE e valor total zero")
        void deveCriarPedidoComEstadoInicial() {
            Pedido pedido = Pedido.criar(1L, cliente, enderecoPedidoValido);

            assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.PENDENTE);
            assertThat(pedido.getValorTotal()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(pedido.getItens()).isEmpty();
            assertThat(pedido.getCliente()).isEqualTo(cliente);
            assertThat(pedido.getEnderecoPedidoEntrega()).isEqualTo(enderecoPedidoValido);
        }

        @Test
        @DisplayName("Deve usar endereço informado ao criar pedido")
        void deveUsarEnderecoInformado() {
            EnderecoPedido enderecoPedidoAlternativo = new EnderecoPedido("Rua B", 200, "Centro",
                    "Florianópolis", "Santa Catarina", "88058208", null);

            Pedido pedido = Pedido.criar(1L, cliente, enderecoPedidoAlternativo);

            assertThat(pedido.getEnderecoPedidoEntrega().cep()).isEqualTo("88058208");
        }

        @Test
        @DisplayName("Deve lançar exceção quando endereço for nulo")
        void deveLancarExcecaoQuandoEnderecoForNulo() {
            assertThatThrownBy(() -> Pedido.criar(1L, cliente, null))
                    .isInstanceOf(EnderecoDoPedidoInvalidoExcecao.class);
        }

        @Test
        @DisplayName("Deve lançar exceção quando idCardapio for nulo")
        void deveLancarExcecaoQuandoIdCardapioForNulo() {
            assertThatThrownBy(() -> Pedido.criar(null, cliente, enderecoPedidoValido))
                    .isInstanceOf(CardapioNaoEncontradoExcecao.class);
        }

        @Test
        @DisplayName("Deve lançar exceção quando idCardapio for zero ou negativo")
        void deveLancarExcecaoQuandoIdCardapioForZeroOuNegativo() {
            assertThatThrownBy(() -> Pedido.criar(0L, cliente, enderecoPedidoValido))
                    .isInstanceOf(CardapioNaoEncontradoExcecao.class);

            assertThatThrownBy(() -> Pedido.criar(-1L, cliente, enderecoPedidoValido))
                    .isInstanceOf(CardapioNaoEncontradoExcecao.class);
        }

        @Test
        @DisplayName("Deve lançar exceção quando cliente for nulo")
        void deveLancarExcecaoQuandoClienteForNulo() {
            assertThatThrownBy(() -> Pedido.criar(1L, null, enderecoPedidoValido))
                    .isInstanceOf(StatusPedidoInvalidoExcecao.class);
        }
    }

    @Nested
    @DisplayName("adicionarItem() e calcularTotal()")
    class AdicionarItemECalcularTotal {

        @Test
        @DisplayName("Deve calcular valor total ao adicionar itens")
        void deveCalcularTotalAoAdicionarItens() {
            Pedido pedido = Pedido.criar(1L, cliente, enderecoPedidoValido);
            ItemPedido item1 = ItemPedidoBuilder.umItemPedido()
                    .comPrecoUnitario(BigDecimal.valueOf(20.00))
                    .comQuantidade(2)
                    .build();
            ItemPedido item2 = ItemPedidoBuilder.umItemPedido()
                    .comPrecoUnitario(BigDecimal.valueOf(10.00))
                    .comQuantidade(1)
                    .build();

            pedido.adicionarItem(item1);
            pedido.adicionarItem(item2);

            assertThat(pedido.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(50.00));
            assertThat(pedido.getItens()).hasSize(2);
        }

        @Test
        @DisplayName("Deve recalcular total ao remover item")
        void deveRecalcularTotalAoRemoverItem() {
            Pedido pedido = Pedido.criar(1L, cliente, enderecoPedidoValido);
            ItemPedido item = ItemPedidoBuilder.umItemPedido()
                    .comPrecoUnitario(BigDecimal.valueOf(30.00))
                    .comQuantidade(1)
                    .build();

            pedido.adicionarItem(item);
            pedido.removerItem(item);

            assertThat(pedido.getValorTotal()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(pedido.getItens()).isEmpty();
        }
    }

    @Nested
    @DisplayName("mudarStatus()")
    class MudarStatus {

        @Test
        @DisplayName("Deve mudar status quando transição for válida")
        void deveMudarStatusQuandoTransicaoForValida() {
            Pedido pedido = Pedido.criar(1L, cliente, enderecoPedidoValido);

            pedido.mudarStatus(StatusPedido.EM_PREPARACAO);

            assertThat(pedido.getStatusPedido()).isEqualTo(StatusPedido.EM_PREPARACAO);
        }

        @Test
        @DisplayName("Deve lançar exceção quando transição for inválida")
        void deveLancarExcecaoQuandoTransicaoForInvalida() {
            Pedido pedido = Pedido.criar(1L, cliente, enderecoPedidoValido);
            pedido.mudarStatus(StatusPedido.EM_PREPARACAO);

            assertThatThrownBy(() -> pedido.mudarStatus(StatusPedido.PENDENTE))
                    .isInstanceOf(StatusPedidoInvalidoExcecao.class);
        }

        @Test
        @DisplayName("Não deve permitir alterar status de pedido cancelado")
        void naoDevePermitirAlterarStatusDePedidoCancelado() {
            Pedido pedido = Pedido.criar(1L, cliente, enderecoPedidoValido);
            pedido.mudarStatus(StatusPedido.CANCELADO);

            assertThatThrownBy(() -> pedido.mudarStatus(StatusPedido.EM_PREPARACAO))
                    .isInstanceOf(StatusPedidoInvalidoExcecao.class);
        }
    }
}