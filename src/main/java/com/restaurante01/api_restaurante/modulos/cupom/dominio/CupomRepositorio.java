package com.restaurante01.api_restaurante.modulos.cupom.dominio;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;

public interface CupomRepositorio {
    Cupom obterPorCodigo(String codigo);
}
