package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;

import java.util.List;
import java.util.Optional;

public interface FidelidadeRepositorio {
    Fidelidade salvar(Fidelidade fidelidade);
    Fidelidade atualizar(Fidelidade fidelidade);
    Optional<Fidelidade> buscarPorId(Long id);
    Optional<Fidelidade> buscarPorClienteId(Long clienteId);
    List<Fidelidade> buscarTodosPorListaDeIds(List<Long> idsCliente);
    boolean existePorClienteId(Long clienteId);
}
