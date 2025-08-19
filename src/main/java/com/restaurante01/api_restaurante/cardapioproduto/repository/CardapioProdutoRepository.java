package com.restaurante01.api_restaurante.cardapioproduto.repository;

import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardapioProdutoRepository extends JpaRepository<CardapioProduto, Long> {
}
