package com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class CupomNaoPodeSerConsumidoExcecao extends RegraDeNegocioExcecao {
    public CupomNaoPodeSerConsumidoExcecao(String message){
        super(message);
    }
}
