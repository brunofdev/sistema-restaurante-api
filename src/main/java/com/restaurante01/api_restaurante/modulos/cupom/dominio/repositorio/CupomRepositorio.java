package com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;

public interface CupomRepositorio {
    Cupom obterPorCodigo(CodigoCupom codigo);
    void salvar(Cupom cupom);
}
