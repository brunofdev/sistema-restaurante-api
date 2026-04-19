package com.restaurante01.api_restaurante.modulos.usuario.operador.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperadorJPA extends JpaRepository<Operador, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<Operador> findByCpf(String cpf);
    Optional<Operador> findByUserName(String userName);
}
