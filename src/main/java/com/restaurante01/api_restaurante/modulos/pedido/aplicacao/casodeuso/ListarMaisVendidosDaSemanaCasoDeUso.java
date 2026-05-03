package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.SolicitarTopProdutoVendidosDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoMaisVendidoSemanal;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItensMaisVendidosPorPeriodo;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ListarMaisVendidosDaSemanaCasoDeUso {

    private final PedidoRepositorio repositorio;

    public ListarMaisVendidosDaSemanaCasoDeUso(PedidoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public ItensMaisVendidosPorPeriodo executar(SolicitarTopProdutoVendidosDTO dto){
        Page<Pedido> pedidosEncontrados = buscarPedidos(LocalDateTime.of(dto.dataIni(), LocalTime.MIN), LocalDateTime.of(dto.dataFim(), LocalTime.MAX));
        verificaSeEncontrouAlgumPedido(pedidosEncontrados, dto);
        List<ItemPedido> itensDoPedido = capturarItensDosPedidos(pedidosEncontrados);
        Map<String, ItemPedidoMaisVendidoSemanal> mapa = agruparPorProdutoVendido(itensDoPedido);
        List<ItemPedidoMaisVendidoSemanal> maisVendidosFiltrados = filtrarMaisVendidos(mapa, dto.quantidadeParaRetornar());
        return new ItensMaisVendidosPorPeriodo(dto.dataIni(), dto.dataFim(), maisVendidosFiltrados);
    }

    private void verificaSeEncontrouAlgumPedido(Page<Pedido> pedidosEncontrados, SolicitarTopProdutoVendidosDTO dto) {
        if (pedidosEncontrados.getTotalElements() == 0) {
            throw new PedidoNaoEncontradoExcecao("Nenhum pedido foi encontrado no periodo enviado: " + dto.dataIni().toString() + " - " + dto.dataFim().toString());
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
    private Map<String, ItemPedidoMaisVendidoSemanal> agruparPorProdutoVendido(List<ItemPedido> itensDoPedido){
        Map<String, ItemPedidoMaisVendidoSemanal> mapaAgrupado = new HashMap<>();
        for(ItemPedido itemPedido : itensDoPedido){
            if (!mapaAgrupado.containsKey(itemPedido.getProduto().nome())) {
                mapaAgrupado.put(itemPedido.getProduto().nome(), new ItemPedidoMaisVendidoSemanal(itemPedido.getProduto().idProduto(), itemPedido.getProduto().nome(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario(), 1));
            }else{
                int qtdVezesPedida = mapaAgrupado.get(itemPedido.getProduto().nome()).qntVezesPedido() + 1;
                int quantidade = mapaAgrupado.get(itemPedido.getProduto().nome()).qtdVendida() + itemPedido.getQuantidade();
                BigDecimal precoMedio = mapaAgrupado.get(itemPedido.getProduto().nome()).precoMedio().add(itemPedido.getPrecoUnitario());
                mapaAgrupado.put(itemPedido.getProduto().nome(), new ItemPedidoMaisVendidoSemanal(itemPedido.getProduto().idProduto(), itemPedido.getProduto().nome(), quantidade, precoMedio, qtdVezesPedida));
            }
        }
        return mapaAgrupado;
    }
    private List<ItemPedidoMaisVendidoSemanal> filtrarMaisVendidos(Map<String, ItemPedidoMaisVendidoSemanal> mapa, Integer quantidadeFiltro) {
        return mapa.values()
                .stream()
                .map(itemPedido -> new ItemPedidoMaisVendidoSemanal(
                        itemPedido.idProduto(), itemPedido.nomeProduto(), itemPedido.qtdVendida(), itemPedido.precoMedio().divide(new BigDecimal(itemPedido.qntVezesPedido()), 2, RoundingMode.HALF_UP), itemPedido.qntVezesPedido()))
                .toList()
                .stream()
                .sorted(Comparator.comparing(ItemPedidoMaisVendidoSemanal::qtdVendida).reversed())
                .limit(quantidadeFiltro)
                .toList();
    }
}