package com.restaurante01.api_restaurante.produto.factory;

import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.mapper.ProdutoMapper;

import java.util.List;
import java.util.Map;

public class ProdutoFactory {
    public static Produto instanciarProduto(ProdutoDTO produtoDTO){
        Produto novoProduto = new Produto();
        novoProduto.setNome(produtoDTO.getNome());
        novoProduto.setPreco(produtoDTO.getPreco());
        novoProduto.setDescricao(produtoDTO.getDescricao());
        novoProduto.setDisponibilidade(produtoDTO.getDisponibilidade());
        novoProduto.setQuantidadeAtual(produtoDTO.getQuantidadeAtual());
        return novoProduto;
    }
    public static void atualizarProduto(Produto produto, ProdutoDTO dto) {
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeAtual(dto.getQuantidadeAtual());
        produto.setDisponibilidade(dto.getDisponibilidade());
    }
    public static List<Produto> atualizarProdutoEmLote (Map<Long, ProdutoDTO> idsMapeados, List<Produto> produtosEncontrados){
        for (Produto produto : produtosEncontrados) {
            ProdutoDTO produtoAtualizado = idsMapeados.get(produto.getId());
            atualizarProduto(produto, produtoAtualizado);
        }
        return produtosEncontrados;
    }
}
