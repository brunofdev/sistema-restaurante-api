package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteRepositorio {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorEmail(String email);
    Optional<Cliente> buscarPorCpf(String cpf); // Adicionado
    Optional<Cliente> buscarPorUserName(String userName); // Adicionado
    Page<Cliente> buscarTodos(Pageable pageable);
    boolean existePorEmail(String email);
    boolean existePorCpf(String cpf); // Adicionado
    boolean existePorUserName(String userName); // Adicionado
    void deletar(Cliente cliente);
}