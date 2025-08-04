package com.restaurante01.api_restaurante.produto;

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
}
