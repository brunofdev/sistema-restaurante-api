package com.restaurante01.api_restaurante.modulos.usuario.operador.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperadorJPA extends JpaRepository<Operador, Long> {
    boolean existsByCpf(Cpf cpf);
    boolean existsByEmail(String email);
    Optional<Operador> findByCpf(Cpf cpf);;
}
