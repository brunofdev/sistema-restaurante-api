package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.AssociacaoNaoExisteExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.QntdCustomizadaInsuficienteExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemValidacaoEstoque;
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

    public void executar(Long idCardapio, List<ItemValidacaoEstoque> itens) {
        List<ItemValidacaoEstoque> itensLimpos = agruparItensRepetidos(itens);
        List<Associacao> estoque = buscarEstoqueNoBanco(idCardapio,  itensLimpos);
        validarPertencimentoAoCardapio(estoque, itensLimpos);
        validarDisponibilidade(estoque, itensLimpos);
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

    private List<Associacao> buscarEstoqueNoBanco(Long idCardapio, List<ItemValidacaoEstoque> itensLimpos) {
        List<Long> idsProdutos = itensLimpos.stream()
                .map(ItemValidacaoEstoque::idProduto)
                .toList();

        return repositorio.buscarItensDoPedido(idCardapio, idsProdutos);
    }

    private void validarPertencimentoAoCardapio(List<Associacao> estoque, List<ItemValidacaoEstoque> itensLimpos) {
        if (estoque.size() != itensLimpos.size()) {
            throw new AssociacaoNaoExisteExcecao("Atenção: Um ou mais produtos solicitados não pertencem a este cardápio.");
        }
    }

    private void validarDisponibilidade(List<Associacao> estoque, List<ItemValidacaoEstoque> itensLimpos) {
        for (ItemValidacaoEstoque itemDesejado : itensLimpos) {
            Associacao itemNoBanco = estoque.stream()
                    .filter(cp -> cp.getProduto().getId().equals(itemDesejado.idProduto()))
                    .findFirst()
                    .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado no estoque."));
            if (!itemNoBanco.verificaDisponibilidadeProduto(itemDesejado.quantidade())) {
                throw new QntdCustomizadaInsuficienteExcecao("Quantidade indisponível para o produto: " + itemNoBanco.getProduto().getNome());
            }
        }
    }
}