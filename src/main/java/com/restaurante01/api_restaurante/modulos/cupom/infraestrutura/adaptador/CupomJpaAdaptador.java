package com.restaurante01.api_restaurante.modulos.cupom.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.CupomRepositorio;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.infraestrutura.persistencia.CupomJPA;
import org.springframework.stereotype.Component;

@Component
public class CupomJpaAdaptador implements CupomRepositorio {

    private final CupomJPA jpa;

    public CupomJpaAdaptador(CupomJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public Cupom obterPorCodigo(String codigo){
        return jpa.findByCodigoCupom(new CodigoCupom(codigo)).orElseThrow(() -> new CupomInvalidoExcecao("Cupom Informado: >> " + codigo +  " << é Inválido"));
    }
}
