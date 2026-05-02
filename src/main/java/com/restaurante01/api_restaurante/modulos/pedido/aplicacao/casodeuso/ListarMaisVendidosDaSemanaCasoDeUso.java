package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoMaisVendidoSemanal;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItensMaisVendidosNaSemana;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
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

    public ItensMaisVendidosNaSemana executar(LocalDateTime dataIni, LocalDateTime dataFim){
       Page<Pedido> pedidosEncontrados = buscarPedidos(dataIni, dataFim);
       List<ItemPedido> itensDoPedido = capturarItensDosPedidos(pedidosEncontrados);
       Map<String, ItemPedidoMaisVendidoSemanal> mapa = agruparProdutosVendidos(itensDoPedido);
       List<ItemPedidoMaisVendidoSemanal> cincoMaisVendidos = filtrarPelosCincoMaisVendidos(mapa);
        return new ItensMaisVendidosNaSemana(dataIni.toString(), dataFim.toString(), cincoMaisVendidos);
    }

    private Page<Pedido> buscarPedidos(LocalDateTime dataIni, LocalDateTime dataFim){
        return repositorio.buscarPorDataCriacaoEntre(dataIni, dataFim, Pageable.unpaged());
    }

    private List<ItemPedido> capturarItensDosPedidos(Page<Pedido> pedidosEncontrados) {
        List<ItemPedido> itensDoPedido = new ArrayList<>();
        for (Pedido pedido : pedidosEncontrados.getContent()) {
            for (ItemPedido itemPedido : pedido.getItens()) {
                itensDoPedido.add(itemPedido);
            }
        }
        return itensDoPedido;
    }


    private Map<String, ItemPedidoMaisVendidoSemanal> agruparProdutosVendidos(List<ItemPedido> itensDoPedido){
        Map<String, ItemPedidoMaisVendidoSemanal> mapaAgrupado = new HashMap<>();
        for(ItemPedido itemPedido : itensDoPedido){
            if (!mapaAgrupado.containsKey(itemPedido.getProduto().nome())) {
                mapaAgrupado.put(itemPedido.getProduto().nome(), new ItemPedidoMaisVendidoSemanal(itemPedido.getProduto().idProduto(), itemPedido.getProduto().nome(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario()));
            }else{
                int quantidade = mapaAgrupado.get(itemPedido.getProduto().nome()).qtdVendida() + itemPedido.getQuantidade();
                BigDecimal precoMedio = mapaAgrupado.get(itemPedido.getProduto().nome()).precoMedio().add(itemPedido.getPrecoUnitario()).divide(new BigDecimal("2"));
                mapaAgrupado.put(itemPedido.getProduto().nome(), new ItemPedidoMaisVendidoSemanal(itemPedido.getProduto().idProduto(), itemPedido.getProduto().nome(), quantidade, precoMedio));
            }
        }
        return mapaAgrupado;
    }

    private List<ItemPedidoMaisVendidoSemanal> filtrarPelosCincoMaisVendidos(Map<String, ItemPedidoMaisVendidoSemanal> mapa) {
        return mapa.values()
                .stream()
                .sorted(Comparator.comparing(ItemPedidoMaisVendidoSemanal::qtdVendida).reversed())
                .limit(5)
                .toList();
    }


}
