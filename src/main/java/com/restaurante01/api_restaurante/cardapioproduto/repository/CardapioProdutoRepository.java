package com.restaurante01.api_restaurante.cardapioproduto.repository;

import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CardapioProdutoRepository extends JpaRepository<CardapioProduto, Long> {


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cardapio_produto cp WHERE cp.cardapio_id = :idCardapio AND cp.produto_id = :idProduto", nativeQuery = true)
    void deleteProdutoFromCardapio(@Param("idCardapio") long idCardapio, @Param("idProduto") long idProduto);
}
