package com.restaurante01.api_restaurante.produto.validator;

import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.exceptions.PrecoProdutoNegativoException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoQntdNegativa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoValidator {
    public void validarProduto(ProdutoDTO produtoParaValidar) {
        if (produtoParaValidar.getQuantidadeAtual() < 0) {
            throw new ProdutoQntdNegativa("A quantidade do produto não pode ser negativa");
        } else if(produtoParaValidar.getPreco() < 0) {
            throw new PrecoProdutoNegativoException("Preço do produto não pode ser negativo");
        }
    }
    public void validarListaDeProdutos(List<ProdutoDTO> produtosParaValidar){
        for(ProdutoDTO produto : produtosParaValidar){
            validarProduto(produto);
        }
    }
}
