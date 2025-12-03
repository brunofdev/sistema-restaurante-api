package com.restaurante01.api_restaurante.usuarios.cliente.repository;

import com.restaurante01.api_restaurante.usuarios.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByUserName(String userName);
}
