package com.restaurante01.api_restaurante.modulos.usuario.operador.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OperadorRepositorioAdapter implements OperadorRepositorio {
    private final OperadorJPA jpa;

    public OperadorRepositorioAdapter(OperadorJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public Operador salvar(Operador operador) {
        return jpa.save(operador);
    }

    @Override
    public Optional<Operador> buscarPorId(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Optional<Operador> buscarPorCpf(String cpf) {
        return jpa.findByCpf(new Cpf(cpf));
    }


    @Override
    public Page<Operador> buscarTodos(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return jpa.existsByCpf(new Cpf(cpf));
    }

    @Override
    public boolean existePorEmail(String email) {
        return jpa.existsByEmail(email);
    }


    @Override
    public void deletar(Operador operador) {
        jpa.delete(operador);
    }
}