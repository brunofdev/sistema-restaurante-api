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
        if(produtoDTO.getNome() == null || produtoDTO.getNome().isEmpty()){
            throw new ProdutoNomeVazioException("Nome não pode ser vazio");
        }
        if(produtoDTO.getDescricao() == null || produtoDTO.getDescricao().isEmpty()){
            throw new ProdutoDescVazioException("Descrição não pode ser vazia");
        }
        if(produtoDTO.getDisponibilidade() == null){
            throw new ProdutoDisponibilidadeVazioException("Disponibilidade não pode ser vazio");
        }
        if(produtoDTO.getPreco() == null){
            throw new ProdutoPrecoVazioException("Preço não pode ser vazio");
        }
        if(produtoDTO.getQuantidadeAtual() < 0) {
            throw new ProdutoQntdNegativa("A quantidade do produto não pode ser negativa");
        }
        if(produtoDTO.getPreco() < 0) {
            throw new PrecoProdutoNegativoException("Preço do produto não pode ser negativo");
        }


    }
    public void validarListaDeProdutos(List<ProdutoDTO> produtosParaValidar){
        for(ProdutoDTO produto : produtosParaValidar){
            validarProduto(produto);
        }
    }
}
