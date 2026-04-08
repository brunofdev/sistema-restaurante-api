package com.restaurante01.api_restaurante.modulos.cliente.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteJPA extends JpaRepository<Cliente, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByUserName(String userName);
    Optional<Cliente> findByEmail(String email);
}