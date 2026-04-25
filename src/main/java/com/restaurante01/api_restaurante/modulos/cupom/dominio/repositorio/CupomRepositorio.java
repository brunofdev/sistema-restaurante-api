package com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;

import java.util.List;
import java.util.Optional;

public interface CupomRepositorio {
    Optional<Cupom> obterPorCodigo(CodigoCupom codigo);
    void salvar(Cupom cupom);
    List<Cupom> obterTodosOsCupons();
    boolean existeCodigoCupom(CodigoCupom codigoCupom);
    Optional<Cupom> obterPorId(Long id);
    void deletarCupom(Cupom cupom);
}
