package com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface OperadorRepositorio {
    Operador salvar(Operador operador);
    Optional<Operador> buscarPorId(Long id);
    Optional<Operador> buscarPorCpf(Cpf cpf);
    Page<Operador> buscarTodos(Pageable pageable);
    boolean existePorCpf(Cpf cpf);
    boolean existePorEmail(Email email);
    void deletar(Operador operador);

}
