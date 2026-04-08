package com.restaurante01.api_restaurante.modulos.operador.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.operador.dominio.entidade.Operador;

import java.util.List;
import java.util.Optional;

public interface OperadorRepositorio {
    Operador salvar(Operador operador);
    Optional<Operador> buscarPorId(Long id);
    Optional<Operador> buscarPorCpf(String cpf);
    Optional<Operador> buscarPorUserName(String userName);
    List<Operador> buscarTodos();
    boolean existePorCpf(String cpf);
    boolean existePorEmail(String email);
    boolean existePorUserName(String userName);

}
