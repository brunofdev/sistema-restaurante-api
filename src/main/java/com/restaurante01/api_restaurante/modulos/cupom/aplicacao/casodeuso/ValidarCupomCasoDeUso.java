package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.CupomRepositorio;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidarCupomCasoDeUso {

    private final CupomRepositorio repositorio;

    public ValidarCupomCasoDeUso(CupomRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cupom executar(String codigo, BigDecimal valorBrutoTotalPedido){
        return repositorio.obterPorCodigo(codigo);
        //precisa implementar regra para validar o cupom, data, quantidade etc...
    }
}
