package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador.associacao.validador;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.PrecoProdutoNegativoException;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoDescricaoInvalidaExcpetion;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoQntdNegativa;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CardapioProdutoValidador {


    public void validarCardapioProdutoAssociacaoEntradaDTO(AssociacaoEntradaDTO dto){
        if(dto.precoCustomizado() != null && dto.precoCustomizado().compareTo(BigDecimal.ZERO) < 0){
            throw new PrecoProdutoNegativoException("O preço customizado não pode ser negativo");
        }
        if(dto.quantidadeCustomizada() != null && dto.quantidadeCustomizada() < 0){
            throw new ProdutoQntdNegativa("A quantidade customizada não pode ser negativa");
        }
        if(dto.descricaoCustomizada() != null && dto.descricaoCustomizada().length() < 10){
            throw new ProdutoDescricaoInvalidaExcpetion("Descrição deve conter mais de 10 caracteres");
        }
    }
}
