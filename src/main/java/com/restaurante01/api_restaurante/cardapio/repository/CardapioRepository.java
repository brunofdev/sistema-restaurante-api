package com.restaurante01.api_restaurante.cardapio.repository;

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    boolean existsByNome(String nome);
    Cardapio findByNome(String nome);

}
