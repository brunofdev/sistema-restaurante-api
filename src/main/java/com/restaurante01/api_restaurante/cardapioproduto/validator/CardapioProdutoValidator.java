package com.restaurante01.api_restaurante.cardapioproduto.validator;

import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioIdNegativoException;
import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioIdVazioException;
import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioMesmoNomeExcepetion;
import com.restaurante01.api_restaurante.cardapioproduto.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.cardapioproduto.exceptions.AssociacaoExistenteCardapioProdutoException;
import com.restaurante01.api_restaurante.cardapioproduto.repository.CardapioProdutoRepository;
import com.restaurante01.api_restaurante.cardapioproduto.service.CardapioProdutoService;
import com.restaurante01.api_restaurante.produto.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardapioProdutoValidator {

    @Autowired
    private CardapioProdutoRepository cardapioProdutoRepository;

    public CardapioProdutoValidator(CardapioProdutoRepository cardapioProdutoRepository){
        this.cardapioProdutoRepository = cardapioProdutoRepository;
    }

    public void validarCardapioProdutoAssociacaoEntradaDTO(CardapioProdutoAssociacaoEntradaDTO dto,  boolean existeAssociacao, boolean estaAtualizandoApenas){
        if(dto.getPrecoCustomizado() < 0){
            throw new PrecoProdutoNegativoException("O preço customizado não pode ser negativo");
        }
        if(dto.getQuantidadeCustomizada() < 0){
            throw new ProdutoQntdNegativa("A quantidade customizada não pode ser negativa");
        }
        if(dto.getDescricaoCustomizada() != null && dto.getDescricaoCustomizada().length() < 10){
            throw new ProdutoDescricaoInvalidaExcpetion("Descrição deve conter mais de 10 caracteres");
        }
        //Aqui, garantimos idepotencia no verbo PUT do controlador
        if(existeAssociacao && !estaAtualizandoApenas){
            throw new AssociacaoExistenteCardapioProdutoException("Já existe associação presente");
        }
    }
}
