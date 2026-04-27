package com.restaurante01.api_restaurante.modulos.pedido.dominio.servico;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.CalculoDescontoCupom;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component("PORCENTAGEM")
public class CalculaDescontoCupomPorcentagem implements CalculoDescontoCupom {

    @Override
    public TipoDesconto regraSuportada(){
        return TipoDesconto.PORCENTAGEM;
    }

    @Override
    public BigDecimal calcularDesconto(BigDecimal valorDesconto, BigDecimal valorBrutoTotalPedido){
        return valorBrutoTotalPedido.multiply(valorDesconto).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }
}
