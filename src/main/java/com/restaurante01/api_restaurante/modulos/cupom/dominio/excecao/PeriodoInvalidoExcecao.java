package com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class PeriodoInvalidoExcecao extends RegraDeNegocioExcecao {
    public PeriodoInvalidoExcecao(String message){
        super(message);
    }
}
