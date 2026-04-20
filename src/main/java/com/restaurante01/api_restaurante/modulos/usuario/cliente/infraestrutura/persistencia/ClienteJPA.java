package com.restaurante01.api_restaurante.modulos.usuario.cliente.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteJPA extends JpaRepository<Cliente, Long> {
    boolean existsByCpf(Cpf cpf);
    boolean existsByEmail(String email);
    Optional<Cliente> findByCpf(Cpf cpf);
    Optional<Cliente> findByEmail(String email);

    List<Cliente> Cpf(Cpf cpf);
}