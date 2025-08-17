package com.restaurante01.api_restaurante.produto.validator;

import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.exceptions.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoValidator {
    public void validarProduto(ProdutoDTO produtoDTO) {
        if(produtoDTO.getId() == null){
            throw new ProdutoIdVazioException("Id não pode ser vazio");
        }
        else if(produtoDTO.getNome().isEmpty() || produtoDTO.getNome() == null){
            throw new ProdutoNomeVazioException("Nome não pode ser vazio");
        }
        else if(produtoDTO.getDescricao() == null || produtoDTO.getDescricao().isEmpty()){
            throw new ProdutoDescVazioException("Descrição não pode ser vazia");
        }
        else if(produtoDTO.getDisponibilidade() == null){
            throw new ProdutoDisponibilidadeVazioException("Disponibilidade não pode ser vazio");
        }
        else if(produtoDTO.getPreco() == null){
            throw new ProdutoPrecoVazioExceptionException("Preço não pode ser vazio");
        }
        else if(produtoDTO.getQuantidadeAtual() < 0) {
            throw new ProdutoQntdNegativa("A quantidade do produto não pode ser negativa");
        }
        else if(produtoDTO.getPreco() < 0) {
            throw new PrecoProdutoNegativoException("Preço do produto não pode ser negativo");
        }


    }
    public void validarListaDeProdutos(List<ProdutoDTO> produtosParaValidar){
        for(ProdutoDTO produto : produtosParaValidar){
            validarProduto(produto);
        }
    }
}
