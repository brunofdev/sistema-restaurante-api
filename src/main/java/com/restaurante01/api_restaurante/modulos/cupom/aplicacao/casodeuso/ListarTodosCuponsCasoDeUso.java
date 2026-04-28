package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.mapeador.CupomMapeador;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTodosCuponsCasoDeUso {

    private final CupomRepositorio cupomRepositorio;
    private final CupomMapeador cupomMapeador;

    public ListarTodosCuponsCasoDeUso(CupomRepositorio cupomRepositorio, CupomMapeador cupomMapeador) {
        this.cupomRepositorio = cupomRepositorio;
        this.cupomMapeador = cupomMapeador;
    }

    public List<CupomAdminDTO> executar(){
        return cupomMapeador.mapearListaDtoDetalhado(cupomRepositorio.obterTodosOsCupons());
    }
}
