package com.restaurante01.api_restaurante.repository;

import com.restaurante01.api_restaurante.entitys.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByDisponibilidade(boolean disponibilidade);

    List<Produto> findByQuantidadeAtualLessThan(int quantidade);

}
