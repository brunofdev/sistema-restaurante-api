package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;

import java.math.BigDecimal;

public interface CalculoDescontoCupom {
    TipoDesconto regraSuportada();
    BigDecimal calcularDesconto(BigDecimal valorDesconto, BigDecimal valorBrutoTotalPedido);
}
