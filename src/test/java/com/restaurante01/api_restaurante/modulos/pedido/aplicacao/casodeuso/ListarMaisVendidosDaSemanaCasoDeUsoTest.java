package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.builders.ProdutoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoMaisVendidoSemanal;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItensMaisVendidosNaSemana;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedidoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.PedidoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarMaisVendidosDaSemanaCasoDeUsoTest {

    @Mock
    PedidoRepositorio repositorio;

    @InjectMocks
    ListarMaisVendidosDaSemanaCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve retornar os 5 itens mais vendidos da semana ordenados por quantidade")
    void deveRetornarOsCincoItensPedidoMaisVendidosDaSemana() {
        Produto xisBacon      = ProdutoBuilder.umProduto().comId(1L).comNome("Xis bacon").build();
        Produto xisSalada     = ProdutoBuilder.umProduto().comId(2L).comNome("Xis salada").build();
        Produto xisFrango     = ProdutoBuilder.umProduto().comId(3L).comNome("Xis frango").build();
        Produto xisStrogonoff = ProdutoBuilder.umProduto().comId(4L).comNome("Xis Strogonoff").build();
        Produto xisMaionese   = ProdutoBuilder.umProduto().comId(5L).comNome("Xis maionese").build();

        // Pedido 1: X-bacon(10), X-salada(20), X-frango(8)
        Pedido pedido1 = PedidoBuilder.umPedido()
                .comItem(ItemPedidoBuilder.umItemPedido().comId(1L).comProduto(xisBacon).comQuantidade(10).comPrecoUnitario(new BigDecimal("25.60")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(2L).comProduto(xisSalada).comQuantidade(20).comPrecoUnitario(new BigDecimal("19.99")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(3L).comProduto(xisFrango).comQuantidade(8).comPrecoUnitario(new BigDecimal("18.20")).build())
                .build();

        // Pedido 2: X-bacon(10), X-salada(25), X-frango(8), X-Strogonoff(39), X-maionese(23)
        Pedido pedido2 = PedidoBuilder.umPedido()
                .comItem(ItemPedidoBuilder.umItemPedido().comId(4L).comProduto(xisBacon).comQuantidade(10).comPrecoUnitario(new BigDecimal("25.60")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(5L).comProduto(xisSalada).comQuantidade(25).comPrecoUnitario(new BigDecimal("19.99")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(6L).comProduto(xisFrango).comQuantidade(8).comPrecoUnitario(new BigDecimal("18.20")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(7L).comProduto(xisStrogonoff).comQuantidade(39).comPrecoUnitario(new BigDecimal("41.56")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(8L).comProduto(xisMaionese).comQuantidade(23).comPrecoUnitario(new BigDecimal("23.45")).build())
                .build();

        LocalDateTime dataIni = LocalDateTime.of(LocalDate.of(2026, 2, 1), LocalTime.now());
        LocalDateTime dataFim = LocalDateTime.of(LocalDate.of(2026, 2, 8), LocalTime.now());

        when(repositorio.buscarPorDataCriacaoEntre(dataIni, dataFim, Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(pedido1, pedido2)));

        ItensMaisVendidosNaSemana resultado = casoDeUso.executar(dataIni, dataFim);

        // Totais esperados: X-salada(45), X-Strogonoff(39), X-maionese(23), X-bacon(20), X-frango(16)
        List<ItemPedidoMaisVendidoSemanal> itens = resultado.itens();
        assertEquals(5,               itens.size());
        assertEquals("Xis salada",     itens.get(0).nomeProduto());
        assertEquals(45,               itens.get(0).qtdVendida());
        assertEquals("Xis Strogonoff", itens.get(1).nomeProduto());
        assertEquals(39,               itens.get(1).qtdVendida());
        assertEquals("Xis maionese",   itens.get(2).nomeProduto());
        assertEquals(23,               itens.get(2).qtdVendida());
        assertEquals("Xis bacon",      itens.get(3).nomeProduto());
        assertEquals(20,               itens.get(3).qtdVendida());
        assertEquals("Xis frango",     itens.get(4).nomeProduto());
        assertEquals(16,               itens.get(4).qtdVendida());
    }
}
