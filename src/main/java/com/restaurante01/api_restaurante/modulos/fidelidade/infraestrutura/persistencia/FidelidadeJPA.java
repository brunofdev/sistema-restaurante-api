package com.restaurante01.api_restaurante.modulos.fidelidade.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FidelidadeJPA extends JpaRepository<Fidelidade, Long> {
    Optional<Fidelidade> findByClienteId(Long clienteId);
    boolean existsByClienteId(Long clienteId);
    List<Fidelidade> findByClienteIdIn(List<Long> clienteId);
}
