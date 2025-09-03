package com.restaurante01.api_restaurante.produto.validator;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoMesmoNomeExistenteException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoNomeInvalidoException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoValidator {
    public void validarProduto(ProdutoDTO produtoDTO, boolean existeProduto) {
        if (existeProduto){
            throw new ProdutoMesmoNomeExistenteException("Já existe Produto cadastrado com o mesmo nome");
        }
        if(produtoDTO.getNome().length() <= 3){
            throw new ProdutoNomeInvalidoException("Nome deve possuir MAIS de três (3) caracteres");
        }
        if(produtoDTO.getNome().length() >= 30){
            throw new ProdutoNomeInvalidoException("Nome deve possuir MENOS (30) caracteres");
        }
    }

    //precisa implementar esta validação em lote
    public void validarListaDeProdutos(List<ProdutoDTO> produtosParaValidar){
       // for(ProdutoDTO produto : produtosParaValidar){
        //
        // }
    }
}
