package com.restaurante01.api_restaurante.modulos.pedido.dominio.servico;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.CalculoDescontoCupom;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("VALOR")
public class CalculaDescontoCupomValor implements CalculoDescontoCupom{

    @Override
    public TipoDesconto regraSuportada(){
        return TipoDesconto.VALOR;
    }

    @Override
    public BigDecimal calcularDesconto(BigDecimal valorDesconto, BigDecimal valorTotalPedido){
        return valorDesconto;
    }
}

