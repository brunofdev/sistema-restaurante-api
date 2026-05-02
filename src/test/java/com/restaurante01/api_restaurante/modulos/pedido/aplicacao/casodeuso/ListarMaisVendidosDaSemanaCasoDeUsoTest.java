package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.builders.ProdutoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.TopProdutosVendidosDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoMaisVendidoSemanal;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItensMaisVendidosNaSemana;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedidoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.PedidoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
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
    @DisplayName("Deve retornar os itens mais vendidos da semana ordenados por quantidade")
    void deveRetornarOsItensMaisVendidosOrdenadosPorQuantidade() {
        Produto xisBacon      = ProdutoBuilder.umProduto().comId(1L).comNome("Xis bacon").build();
        Produto xisSalada     = ProdutoBuilder.umProduto().comId(2L).comNome("Xis salada").build();
        Produto xisFrango     = ProdutoBuilder.umProduto().comId(3L).comNome("Xis frango").build();
        Produto xisStrogonoff = ProdutoBuilder.umProduto().comId(4L).comNome("Xis Strogonoff").build();
        Produto xisMaionese   = ProdutoBuilder.umProduto().comId(5L).comNome("Xis maionese").build();

        Pedido pedido1 = PedidoBuilder.umPedido()
                .comItem(ItemPedidoBuilder.umItemPedido().comId(1L).comProduto(xisBacon).comQuantidade(10).comPrecoUnitario(new BigDecimal("25.60")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(2L).comProduto(xisSalada).comQuantidade(20).comPrecoUnitario(new BigDecimal("19.99")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(3L).comProduto(xisFrango).comQuantidade(8).comPrecoUnitario(new BigDecimal("18.20")).build())
                .build();

        Pedido pedido2 = PedidoBuilder.umPedido()
                .comItem(ItemPedidoBuilder.umItemPedido().comId(4L).comProduto(xisBacon).comQuantidade(10).comPrecoUnitario(new BigDecimal("25.60")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(5L).comProduto(xisSalada).comQuantidade(25).comPrecoUnitario(new BigDecimal("19.99")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(6L).comProduto(xisFrango).comQuantidade(8).comPrecoUnitario(new BigDecimal("18.20")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(7L).comProduto(xisStrogonoff).comQuantidade(39).comPrecoUnitario(new BigDecimal("41.56")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(8L).comProduto(xisMaionese).comQuantidade(23).comPrecoUnitario(new BigDecimal("23.45")).build())
                .build();

        LocalDateTime dataIni = LocalDateTime.of(LocalDate.of(2026, 2, 1), LocalTime.now());
        LocalDateTime dataFim = LocalDateTime.of(LocalDate.of(2026, 2, 8), LocalTime.now());
        TopProdutosVendidosDTO dto = new TopProdutosVendidosDTO(dataIni, dataFim, 5);

        when(repositorio.buscarPorDataCriacaoEntre(dataIni, dataFim, Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(pedido1, pedido2)));

        ItensMaisVendidosNaSemana resultado = casoDeUso.executar(dto);

        // Totais: X-salada(45), X-Strogonoff(39), X-maionese(23), X-bacon(20), X-frango(16)
        List<ItemPedidoMaisVendidoSemanal> itens = resultado.itens();
        assertEquals(5,                itens.size());
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

    @Test
    @DisplayName("Deve lançar exceção quando nenhum pedido for encontrado no período")
    void deveLancarExcecaoQuandoNenhumPedidoForEncontradoNoPeriodo() {
        LocalDateTime dataIni = LocalDateTime.of(LocalDate.of(2026, 1, 1), LocalTime.MIDNIGHT);
        LocalDateTime dataFim = LocalDateTime.of(LocalDate.of(2026, 1, 7), LocalTime.MIDNIGHT);
        TopProdutosVendidosDTO dto = new TopProdutosVendidosDTO(dataIni, dataFim, 5);

        when(repositorio.buscarPorDataCriacaoEntre(dataIni, dataFim, Pageable.unpaged()))
                .thenReturn(Page.empty());

        assertThrows(PedidoNaoEncontradoExcecao.class, () -> casoDeUso.executar(dto));
    }

    @Test
    @DisplayName("Deve respeitar a quantidade solicitada para retornar")
    void deveRespeitarAQuantidadeSolicitadaParaRetornar() {
        Produto xisBacon      = ProdutoBuilder.umProduto().comId(1L).comNome("Xis bacon").build();
        Produto xisSalada     = ProdutoBuilder.umProduto().comId(2L).comNome("Xis salada").build();
        Produto xisFrango     = ProdutoBuilder.umProduto().comId(3L).comNome("Xis frango").build();
        Produto xisStrogonoff = ProdutoBuilder.umProduto().comId(4L).comNome("Xis Strogonoff").build();
        Produto xisMaionese   = ProdutoBuilder.umProduto().comId(5L).comNome("Xis maionese").build();

        Pedido pedido = PedidoBuilder.umPedido()
                .comItem(ItemPedidoBuilder.umItemPedido().comId(1L).comProduto(xisBacon).comQuantidade(10).comPrecoUnitario(new BigDecimal("25.60")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(2L).comProduto(xisSalada).comQuantidade(45).comPrecoUnitario(new BigDecimal("19.99")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(3L).comProduto(xisFrango).comQuantidade(16).comPrecoUnitario(new BigDecimal("18.20")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(4L).comProduto(xisStrogonoff).comQuantidade(39).comPrecoUnitario(new BigDecimal("41.56")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(5L).comProduto(xisMaionese).comQuantidade(23).comPrecoUnitario(new BigDecimal("23.45")).build())
                .build();

        LocalDateTime dataIni = LocalDateTime.of(LocalDate.of(2026, 2, 1), LocalTime.MIDNIGHT);
        LocalDateTime dataFim = LocalDateTime.of(LocalDate.of(2026, 2, 8), LocalTime.MIDNIGHT);
        TopProdutosVendidosDTO dto = new TopProdutosVendidosDTO(dataIni, dataFim, 3);

        when(repositorio.buscarPorDataCriacaoEntre(dataIni, dataFim, Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(pedido)));

        ItensMaisVendidosNaSemana resultado = casoDeUso.executar(dto);

        // Existem 5 produtos mas solicitou apenas 3 — deve retornar somente os 3 mais vendidos
        List<ItemPedidoMaisVendidoSemanal> itens = resultado.itens();
        assertEquals(3,                itens.size());
        assertEquals("Xis salada",     itens.get(0).nomeProduto());
        assertEquals("Xis Strogonoff", itens.get(1).nomeProduto());
        assertEquals("Xis maionese",   itens.get(2).nomeProduto());
    }

    @Test
    @DisplayName("Deve retornar menos itens quando houver menos produtos distintos que o solicitado")
    void deveRetornarMenosItensQuandoHouverMenosProdutosDistintosQueOSolicitado() {
        Produto xisBacon  = ProdutoBuilder.umProduto().comId(1L).comNome("Xis bacon").build();
        Produto xisSalada = ProdutoBuilder.umProduto().comId(2L).comNome("Xis salada").build();

        Pedido pedido = PedidoBuilder.umPedido()
                .comItem(ItemPedidoBuilder.umItemPedido().comId(1L).comProduto(xisBacon).comQuantidade(10).comPrecoUnitario(new BigDecimal("25.60")).build())
                .comItem(ItemPedidoBuilder.umItemPedido().comId(2L).comProduto(xisSalada).comQuantidade(20).comPrecoUnitario(new BigDecimal("19.99")).build())
                .build();

        LocalDateTime dataIni = LocalDateTime.of(LocalDate.of(2026, 2, 1), LocalTime.MIDNIGHT);
        LocalDateTime dataFim = LocalDateTime.of(LocalDate.of(2026, 2, 8), LocalTime.MIDNIGHT);
        TopProdutosVendidosDTO dto = new TopProdutosVendidosDTO(dataIni, dataFim, 10);

        when(repositorio.buscarPorDataCriacaoEntre(dataIni, dataFim, Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(pedido)));

        ItensMaisVendidosNaSemana resultado = casoDeUso.executar(dto);

        // Solicitou 10 mas só existem 2 produtos distintos — deve retornar 2
        assertEquals(2, resultado.itens().size());
    }
}
