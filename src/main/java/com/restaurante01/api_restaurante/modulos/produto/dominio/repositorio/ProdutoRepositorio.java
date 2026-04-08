package com.restaurante01.api_restaurante.modulos.produto.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProdutoRepositorio {
    List<Produto> findAll();
    List<Produto> findByDisponibilidade(boolean disponibilidade);
    Optional<Produto> findById(Long id);
    boolean existsByNome(String nome);
    List<Produto> findByQuantidadeAtualLessThan(int quantidade);
    Produto save(Produto produto);
    List<Produto> findAllById(Set<Long> ids);
    List<Produto> saveAll(List<Produto> produtos);
    void delete(Produto produto);
}
