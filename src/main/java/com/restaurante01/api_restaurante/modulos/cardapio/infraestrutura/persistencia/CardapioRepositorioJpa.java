package com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardapioRepositorioJpa extends JpaRepository<Cardapio, Long> {

    boolean existsByNome(String nome);
    Cardapio findByNome(String nome);

}
