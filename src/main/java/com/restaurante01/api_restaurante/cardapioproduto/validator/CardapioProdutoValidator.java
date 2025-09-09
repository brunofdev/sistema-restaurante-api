package com.restaurante01.api_restaurante.cardapioproduto.validator;

import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioIdNegativoException;
import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioIdVazioException;
import com.restaurante01.api_restaurante.cardapioproduto.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.exceptions.AssociacaoExistenteCardapioProdutoException;
import com.restaurante01.api_restaurante.produto.exceptions.PrecoProdutoNegativoException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoIdNegativoException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoIdVazioException;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoQntdNegativa;
import org.springframework.stereotype.Component;

@Component
public class CardapioProdutoValidator {
    public void validarCardapioProdutoAssociacaoEntradaDTO(CardapioProdutoAssociacaoEntradaDTO dto, boolean existeAssociacao){
        if(existeAssociacao){
            throw new AssociacaoExistenteCardapioProdutoException("Produto já associadao ao Cardapio");
        }
        if(dto.getIdCardapio() == null || dto.getIdCardapio() == 0){
            throw new CardapioIdVazioException("ID do Cardapio não pode ser zero");
        }
        if(dto.getIdCardapio() < 0){
            throw new CardapioIdNegativoException("O id do cardapio não pode ser valor negativo");
        }
        if(dto.getIdProduto() == null || dto.getIdProduto() == 0){
            throw new ProdutoIdVazioException("Id do Produto não pode ser zero");
        }
        if(dto.getIdProduto() < 0){
            throw new ProdutoIdNegativoException("O id do produto não pode ser um valor negativo");
        }
        if(dto.getPrecoCustomizado() < 0){
            throw new PrecoProdutoNegativoException("O preço customizado não pode ser negativo");
        }
        if(dto.getQuantidadeCustomizada() < 0){
            throw new ProdutoQntdNegativa("A quantidade customizada não pode ser negativa");
        }
    }
}
