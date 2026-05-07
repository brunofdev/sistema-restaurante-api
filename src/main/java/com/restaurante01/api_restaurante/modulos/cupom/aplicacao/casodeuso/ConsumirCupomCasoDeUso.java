package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsumirCupomCasoDeUso {

    private final CupomRepositorio repositorio;

    public ConsumirCupomCasoDeUso(CupomRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cupom executar (CodigoCupom codigoCupom){
        Cupom cupom = buscarPorCodigo(codigoCupom);
        cupom.subtrairQuantidade();
        repositorio.salvar(cupom);
        return cupom;
    }
    private  Cupom buscarPorCodigo (CodigoCupom codigoCupom){
        return repositorio.obterPorCodigo(codigoCupom).orElseThrow(() -> new CupomInvalidoExcecao("Cupom Informado: >> " + codigoCupom +  " << é Inválido"));
    }
}
