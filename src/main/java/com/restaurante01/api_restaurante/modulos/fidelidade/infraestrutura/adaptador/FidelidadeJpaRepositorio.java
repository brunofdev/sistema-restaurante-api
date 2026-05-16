package com.restaurante01.api_restaurante.modulos.fidelidade.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio.FidelidadeRepositorio;
import com.restaurante01.api_restaurante.modulos.fidelidade.infraestrutura.persistencia.FidelidadeJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FidelidadeJpaRepositorio implements FidelidadeRepositorio {

    private final FidelidadeJPA jpa;

    @Override
    public Fidelidade salvar(Fidelidade fidelidade) {
        return jpa.save(fidelidade);
    }

    @Override
    public Fidelidade atualizar(Fidelidade fidelidade) {
        return jpa.save(fidelidade);
    }

    @Override
    public Optional<Fidelidade> buscarPorId(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Optional<Fidelidade> buscarPorClienteId(Long clienteId) {
        return jpa.findByClienteId(clienteId);
    }

    @Override
    public boolean existePorClienteId(Long clienteId) {
        return jpa.existsByClienteId(clienteId);
    }
}
