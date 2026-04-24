package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTodosCuponsCasoDeUso {

    private final CupomRepositorio cupomRepositorio;

    public ListarTodosCuponsCasoDeUso(CupomRepositorio cupomRepositorio) {
        this.cupomRepositorio = cupomRepositorio;
    }

    public List<Cupom> executar(){
        return cupomRepositorio.obterTodosOsCupons();
    };
}
