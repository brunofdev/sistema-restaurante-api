package com.restaurante01.api_restaurante.modulos.usuario.operador.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.usuario.operador.infraestrutura.persistencia.OperadorJPA;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class OperadorJpaAdapter implements OperadorRepositorio {
    private final OperadorJPA jpa;

    @Override
    public Operador salvar(Operador operador) {
        return jpa.save(operador);
    }

    @Override
    public Optional<Operador> buscarPorId(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Optional<Operador> buscarPorCpf(Cpf cpf) {
        return jpa.findByCpf(cpf);
    }


    @Override
    public Page<Operador> buscarTodos(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public boolean existePorCpf(Cpf cpf) {
        return jpa.existsByCpf(cpf);
    }

    @Override
    public boolean existePorEmail(Email email) {
        return jpa.existsByEmail(email);
    }


    @Override
    public void deletar(Operador operador) {
        jpa.delete(operador);
    }
}