package com.restaurante01.api_restaurante.usuarios.operador.repository;

import com.restaurante01.api_restaurante.usuarios.operador.entity.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperadorRepository extends JpaRepository<Long, Operador> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<Operador> findByCpf(String cpf);
    Optional<Operador> findByUserName(String userName);
}
