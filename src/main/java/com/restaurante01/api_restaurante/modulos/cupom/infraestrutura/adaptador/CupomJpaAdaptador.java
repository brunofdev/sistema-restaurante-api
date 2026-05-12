package com.restaurante01.api_restaurante.modulos.cupom.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.repositorio.CupomRepositorio;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cupom.infraestrutura.persistencia.CupomJPA;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CupomJpaAdaptador implements CupomRepositorio {

    private final CupomJPA jpa;


    @Override
    public Optional<Cupom> obterPorCodigo(CodigoCupom codigo){
        return jpa.findByCodigoCupom(codigo);
    }

    @Override
    public void salvar(Cupom cupom){
        jpa.save(cupom);
    }

    @Override
    public List<Cupom> obterTodosOsCupons(){
        return jpa.findAll();
    }

    @Override
    public boolean existeCodigoCupom(CodigoCupom codigo){
        return jpa.existsByCodigoCupom(codigo);
    }

    @Override
    public Optional<Cupom> obterPorId(Long id){
        return jpa.findById(id);
    }
    @Override
    public void deletarCupom (Cupom cupom){
        jpa.delete(cupom);
    }

}
