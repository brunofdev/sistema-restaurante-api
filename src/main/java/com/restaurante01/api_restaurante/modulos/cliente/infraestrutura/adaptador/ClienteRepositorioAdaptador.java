package com.restaurante01.api_restaurante.modulos.cliente.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.cliente.infraestrutura.persistencia.ClienteJPA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteRepositorioAdaptador implements ClienteRepositorio {

    private final ClienteJPA jpa;

    public ClienteRepositorioAdaptador(ClienteJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        return jpa.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Optional<Cliente> buscarPorEmail(String email) {
        return jpa.findByEmail(email);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return jpa.findByCpf(cpf);
    }

    @Override
    public Optional<Cliente> buscarPorUserName(String userName) {
        return jpa.findByUserName(userName);
    }

    @Override
    public Page<Cliente> buscarTodos(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public boolean existePorEmail(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return jpa.existsByCpf(cpf);
    }

    @Override
    public boolean existePorUserName(String userName) {
        return jpa.existsByUserName(userName);
    }

    @Override
    public void deletar(Cliente cliente) {
        jpa.delete(cliente);
    }
}