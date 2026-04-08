package com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import java.util.List;
import java.util.Optional;

public interface CardapioRepositorio {
    Optional<Cardapio> findById(Long id);
    List<Cardapio> findAll();
    boolean existsByNome(String nome);
    Cardapio save(Cardapio cardapio);
    void delete(Cardapio cardapio);
}