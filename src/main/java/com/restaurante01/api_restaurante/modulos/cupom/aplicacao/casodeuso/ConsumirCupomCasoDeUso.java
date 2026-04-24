package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.CupomUtilizado;
import org.springframework.stereotype.Component;

@Component
public class ConsumirCupomCasoDeUso {

    private final CupomRepositorio repositorio;

    public ConsumirCupomCasoDeUso(CupomRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cupom executar (CodigoCupom codigoCupom){
        Cupom cupom = repositorio.obterPorCodigo(codigoCupom);
        cupom.subtrairQuantidade();
        repositorio.salvar(cupom);
        return cupom;
    }
}
