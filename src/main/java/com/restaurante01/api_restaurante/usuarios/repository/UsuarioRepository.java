package com.restaurante01.api_restaurante.usuarios.repository;

import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByUserName(String userName);
}
