package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesCupom;

import java.math.BigDecimal;

//Cupom deve cumprir este contrato
public interface PedidoCupomPorta {
    InformacoesCupom validarCupom(String codigoCupom, BigDecimal valorTotalPedido);
}
