package com.restaurante01.api_restaurante.produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoParaProdutoDTO {
    public static List<ProdutoDTO> converterVariosProdutos(List<Produto>produtos){
        List<ProdutoDTO> produtosDto = new ArrayList<>();
        for(Produto produto : produtos){
            ProdutoDTO produtoDTO = new ProdutoDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPreco(),
                    produto.getQuantidadeAtual(),
                    produto.getDisponibilidade()
            );
            produtosDto.add(produtoDTO);
        }
        return produtosDto;
    }
    public static ProdutoDTO converterUmProduto(Produto produto){
         ProdutoDTO produtoConvertidoDTO = new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeAtual(),
                produto.getDisponibilidade()
        );
         return produtoConvertidoDTO;
    }


}
