package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.QntdCustomizadaInsuficienteException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemValidacaoEstoque;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidarEstoquePedidoUseCase {

    private final CardapioProdutoRepositorio repositorio;

    public ValidarEstoquePedidoUseCase(CardapioProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<CardapioProduto> executar(Long idCardapio, List<ItemValidacaoEstoque> itens) {
        List<ItemValidacaoEstoque> itensLimpos = agruparItensRepetidos(itens);
        List<CardapioProduto> estoque = buscarEstoqueNoBanco(idCardapio,  itensLimpos);
        validarPertencimentoAoCardapio(estoque, itensLimpos);
        validarDisponibilidade(estoque, itensLimpos);
        return estoque;
    }


    private List<ItemValidacaoEstoque> agruparItensRepetidos(List<ItemValidacaoEstoque> itensSujos) {
        return itensSujos.stream()
                .collect(Collectors.groupingBy(
                        ItemValidacaoEstoque::idProduto,
                        Collectors.summingInt(ItemValidacaoEstoque::quantidade)
                ))
                .entrySet().stream()
                .map(entry -> new ItemValidacaoEstoque(entry.getKey(), entry.getValue()))
                .toList();
    }

    private List<CardapioProduto> buscarEstoqueNoBanco(Long idCardapio, List<ItemValidacaoEstoque> itensLimpos) {
        List<Long> idsProdutos = itensLimpos.stream()
                .map(ItemValidacaoEstoque::idProduto)
                .toList();

        return repositorio.buscarItensDoPedido(idCardapio, idsProdutos);
    }

    private void validarPertencimentoAoCardapio(List<CardapioProduto> estoque, List<ItemValidacaoEstoque> itensLimpos) {
        if (estoque.size() != itensLimpos.size()) {
            throw new AssociacaoNaoExisteException("Atenção: Um ou mais produtos solicitados não pertencem a este cardápio.");
        }
    }

    private void validarDisponibilidade(List<CardapioProduto> estoque, List<ItemValidacaoEstoque> itensLimpos) {
        for (ItemValidacaoEstoque itemDesejado : itensLimpos) {
            CardapioProduto itemNoBanco = estoque.stream()
                    .filter(cp -> cp.getProduto().getId().equals(itemDesejado.idProduto()))
                    .findFirst()
                    .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado no estoque."));
            if (!itemNoBanco.verificaDisponibilidadeProduto(itemDesejado.quantidade())) {
                throw new QntdCustomizadaInsuficienteException("Quantidade indisponível para o produto: " + itemNoBanco.getProduto().getNome());
            }
        }
    }
}