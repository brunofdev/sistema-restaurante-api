package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteRepositorio {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorEmail(Email email);
    Optional<Cliente> buscarPorCpf(Cpf cpf);
    Page<Cliente> buscarTodos(Pageable pageable);
    boolean existePorEmail(Email email);
    boolean existePorCpf(Cpf cpf);
    void deletar(Cliente cliente);
}