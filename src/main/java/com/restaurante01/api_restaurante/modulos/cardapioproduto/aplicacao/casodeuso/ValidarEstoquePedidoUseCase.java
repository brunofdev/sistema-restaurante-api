package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.BusinessException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidarEstoquePedidoUseCase {

    private final CardapioProdutoRepositorio repositorio;

    public ValidarEstoquePedidoUseCase(CardapioProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<CardapioProduto> executar(PedidoCriacaoDTO pedido) {
        List<ItemPedidoSolicitadoDTO> itensLimpos = agruparItensRepetidos(pedido.itens());
        List<CardapioProduto> estoque = buscarEstoqueNoBanco(pedido.idCardapio(), itensLimpos);
        validarPertencimentoAoCardapio(estoque, itensLimpos);
        validarDisponibilidade(estoque, itensLimpos);
        return estoque;
    }


    private List<ItemPedidoSolicitadoDTO> agruparItensRepetidos(List<ItemPedidoSolicitadoDTO> itensSujos) {
        return itensSujos.stream()
                .collect(Collectors.groupingBy(
                        ItemPedidoSolicitadoDTO::idProduto,
                        Collectors.summingInt(ItemPedidoSolicitadoDTO::quantidade)
                ))
                .entrySet().stream()
                .map(entry -> new ItemPedidoSolicitadoDTO(entry.getKey(), entry.getValue(), null))
                .toList();
    }

    private List<CardapioProduto> buscarEstoqueNoBanco(Long idCardapio, List<ItemPedidoSolicitadoDTO> itensLimpos) {
        List<Long> idsProdutos = itensLimpos.stream()
                .map(ItemPedidoSolicitadoDTO::idProduto)
                .toList();

        return repositorio.buscarItensDoPedido(idCardapio, idsProdutos);
    }

    private void validarPertencimentoAoCardapio(List<CardapioProduto> estoque, List<ItemPedidoSolicitadoDTO> itensLimpos) {
        if (estoque.size() != itensLimpos.size()) {
            throw new BusinessException("Atenção: Um ou mais produtos solicitados não pertencem a este cardápio.");
        }
    }

    private void validarDisponibilidade(List<CardapioProduto> estoque, List<ItemPedidoSolicitadoDTO> itensLimpos) {
        for (ItemPedidoSolicitadoDTO itemDesejado : itensLimpos) {

            CardapioProduto itemNoBanco = estoque.stream()
                    .filter(cp -> cp.getProduto().getId().equals(itemDesejado.idProduto()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("Produto não encontrado no estoque."));

            if (!itemNoBanco.verificaDisponibilidadeProduto(itemDesejado.quantidade())) {
                throw new BusinessException("Quantidade indisponível para o produto: " + itemNoBanco.getProduto().getNome());
            }
        }
    }
}