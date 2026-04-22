package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.CupomUtilizado;
import org.springframework.stereotype.Component;


@Component
public class ConsumirCupomCasoDeUso {

    private final ValidarCupomCasoDeUso validarCupomCasoDeUso;

    public ConsumirCupomCasoDeUso(ValidarCupomCasoDeUso validarCupomCasoDeUso) {
        this.validarCupomCasoDeUso = validarCupomCasoDeUso;
    }

    public Cupom executar (CupomUtilizado cupomUtilizado){
        return validarCupomCasoDeUso.executar(cupomUtilizado.codigoCupom(), cupomUtilizado.valorBrutoTotalPedido());// aqui ele deve garantir que o cupom é válido
    }
}
