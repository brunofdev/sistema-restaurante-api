package com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface OperadorRepositorio {
    Operador salvar(Operador operador);
    Optional<Operador> buscarPorId(Long id);
    Optional<Operador> buscarPorCpf(String cpf);
    Page<Operador> buscarTodos(Pageable pageable);
    boolean existePorCpf(String cpf);
    boolean existePorEmail(String email);
    void deletar(Operador operador);

}
