package com.restaurante01.api_restaurante.produto.validator;

import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoValidator {
    public void validarProduto(ProdutoDTO produtoDTO) {

    }
    public void validarListaDeProdutos(List<ProdutoDTO> produtosParaValidar){
        for(ProdutoDTO produto : produtosParaValidar){
            validarProduto(produto);
        }
    }
}
