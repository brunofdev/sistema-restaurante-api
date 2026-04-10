package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.validador;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoExistenteCardapioProdutoException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.infraestrutura.persistencia.CardapioProdutoJPA;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.PrecoProdutoNegativoException;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoDescricaoInvalidaExcpetion;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoQntdNegativa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CardapioProdutoValidator {

    @Autowired
    private CardapioProdutoRepositorio CardapioProdutoRepositorio;

    public CardapioProdutoValidator(CardapioProdutoRepositorio CardapioProdutoRepositorio){
        this.CardapioProdutoRepositorio = CardapioProdutoRepositorio;
    }

    public void validarCardapioProdutoAssociacaoEntradaDTO(CardapioProdutoAssociacaoEntradaDTO dto,  boolean existeAssociacao, boolean estaAtualizandoApenas){
        if(dto.getPrecoCustomizado().compareTo(BigDecimal.ZERO) < 0){
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
