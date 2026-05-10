package com.restaurante01.api_restaurante.modulos.usuario.cliente.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.infraestrutura.persistencia.ClienteJPA;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ClienteJpaAdaptador implements ClienteRepositorio {
    private final ClienteJPA jpa;

    @Override
    public Cliente salvar(Cliente cliente) {
        return jpa.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Optional<Cliente> buscarPorEmail(Email email) {
        return jpa.findByEmail(email);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(Cpf cpf) {
        return jpa.findByCpf(cpf);
    }

    @Override
    public Page<Cliente> buscarTodos(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public boolean existePorEmail(Email email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public boolean existePorCpf(Cpf cpf) {
        return jpa.existsByCpf(cpf);
    }

    @Override
    public void deletar(Cliente cliente) {
        jpa.delete(cliente);
    }
}