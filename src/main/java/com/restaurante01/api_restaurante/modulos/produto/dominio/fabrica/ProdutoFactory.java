package com.restaurante01.api_restaurante.modulos.produto.dominio.fabrica;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;

public class ProdutoFactory {
    public static Produto instanciarProduto(ProdutoDTO produtoDTO){
        Produto novoProduto = new Produto();
        novoProduto.setNome(produtoDTO.getNome());
        novoProduto.setPreco(produtoDTO.getPreco());
        novoProduto.setDescricao(produtoDTO.getDescricao());
        novoProduto.setDisponibilidade(produtoDTO.getDisponibilidade());
        novoProduto.setQuantidadeAtual(produtoDTO.getQuantidadeAtual());
        System.out.println(produtoDTO);
        return novoProduto;

    }

}
