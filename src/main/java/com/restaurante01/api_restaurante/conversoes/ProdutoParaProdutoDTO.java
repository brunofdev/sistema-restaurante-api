package com.restaurante01.api_restaurante.conversoes;

import com.restaurante01.api_restaurante.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.entitys.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoParaProdutoDTO {
    public static List<ProdutoDTO> converterParaProduto(List<Produto>produtos){
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


}
