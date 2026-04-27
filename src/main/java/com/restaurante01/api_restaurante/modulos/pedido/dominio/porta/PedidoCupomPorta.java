package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.CupomUtilizado;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.CupomConsumido;

public interface PedidoCupomPorta {
    CupomConsumido validarCupom(CupomUtilizado cupomUtilizado);
}
