package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import org.springframework.stereotype.Service;

@Service
public class DeletarCupomCasoDeUso {

   private final  CupomRepositorio repositorio;

    public DeletarCupomCasoDeUso(CupomRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    //criar teste
    public void executar (Long id){
        Cupom cupom = repositorio.obterPorId(id).orElseThrow(() -> new CupomInvalidoExcecao("Cupom não encontrado com o id: " + id));;
        this.repositorio.deletarCupom(cupom);
    }
}
