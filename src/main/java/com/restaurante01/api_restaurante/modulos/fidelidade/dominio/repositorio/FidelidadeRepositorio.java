package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;

import java.util.Optional;

public interface FidelidadeRepositorio {
    Fidelidade salvar(Fidelidade fidelidade);
    Fidelidade atualizar(Fidelidade fidelidade);
    Optional<Fidelidade> buscarPorId(Long id);
    Optional<Fidelidade> buscarPorClienteId(Long clienteId);
    boolean existePorClienteId(Long clienteId);
}
