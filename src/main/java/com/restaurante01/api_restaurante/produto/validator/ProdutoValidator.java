package com.restaurante01.api_restaurante.produto.validator;

import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoQntdNegativa;
import org.springframework.stereotype.Component;

@Component
public class ProdutoValidator {
    public void validarProduto(ProdutoDTO produtoRecebidoDTO) {
        if (produtoRecebidoDTO.getQuantidadeAtual() < 0) {
            throw new ProdutoQntdNegativa("A quantidade do produto não pode ser negativa");
        }
    }
}
