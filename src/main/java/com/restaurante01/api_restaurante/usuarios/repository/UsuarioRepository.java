package com.restaurante01.api_restaurante.usuarios.repository;

import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
