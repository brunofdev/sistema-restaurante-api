package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.TopProdutosVendidosDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoMaisVendidoSemanal;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItensMaisVendidosNaSemana;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListarMaisVendidosDaSemanaCasoDeUso {

    private final PedidoRepositorio repositorio;

    public ListarMaisVendidosDaSemanaCasoDeUso(PedidoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public ItensMaisVendidosNaSemana executar(TopProdutosVendidosDTO dto) {
       Page<Pedido> pedidosEncontrados = buscarPedidos(dto.dataIni(), dto.dataFim());
       verificaSePedidosForamEncontrados(pedidosEncontrados, dto);
       List<ItemPedido> itensDoPedido = capturarItensDosPedidos(pedidosEncontrados);
       List<ItemPedidoMaisVendidoSemanal> cincoMaisVendidos = agrupaItensESelecionaMaisVendido(itensDoPedido, dto.quantidadeParaRetornar());
        return new ItensMaisVendidosNaSemana(dto.dataIni().toString(), dto.dataFim().toString(), cincoMaisVendidos);
    }

    private void verificaSePedidosForamEncontrados(Page<Pedido> pedidosEncontrados, TopProdutosVendidosDTO dto) {
        if (pedidosEncontrados.getTotalElements() == 0) {
            throw new PedidoNaoEncontradoExcecao("Nenhum pedido foi encontrado no periodo enviado: "  + dto.dataIni().toString() + " - " + dto.dataFim().toString());
        }
    }

    private Page<Pedido> buscarPedidos(LocalDateTime dataIni, LocalDateTime dataFim){
        return repositorio.buscarPorDataCriacaoEntre(dataIni, dataFim, Pageable.unpaged());
    }

    private List<ItemPedido> capturarItensDosPedidos(Page<Pedido> pedidosEncontrados) {
        return pedidosEncontrados.getContent().stream()
                .flatMap(pedido -> pedido.getItens().stream())
                .toList();
    }

    //PRECO MÉDIO ESTA CALCULANDO ERRADO, É COMPLEXO DE SE FAZER, POIS CADA PEDIDO PODE SER VENDIDO POR UM VALOR DEPENDENDO DO DIA E CARDAPIO ONDE ELE FOI VENDIDO
    private List<ItemPedidoMaisVendidoSemanal>  agrupaItensESelecionaMaisVendido(List<ItemPedido> itensDoPedido, Integer quantidadeParaRetornar) {
        return itensDoPedido.stream()
                .collect(Collectors.toMap(
                        item -> item.getProduto().nome(),
                        item ->  new ItemPedidoMaisVendidoSemanal(item.getProduto().idProduto(), item.getProduto().nome(), item.getQuantidade(), item.getPrecoUnitario()),
                        (item1, item2) -> {
                                    int quantidade = item1.qtdVendida() + item2.qtdVendida();
                                    BigDecimal precoMedio = item1.precoMedio().add(item2.precoMedio()).divide(new BigDecimal("2"));
                                    return new ItemPedidoMaisVendidoSemanal(item1.idProduto(), item1.nomeProduto(), quantidade, precoMedio);
                                })
                        )
                .values()
                .stream()
                .sorted(Comparator.comparing(ItemPedidoMaisVendidoSemanal::qtdVendida).reversed())
                .limit(quantidadeParaRetornar)
                .toList();
    }
}
