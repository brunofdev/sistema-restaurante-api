package com.restaurante01.api_restaurante.modulos.produto.infraestrutura;

import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.repositorio.ProdutoRepositorio;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ProdutoRepositorioAdapter implements ProdutoRepositorio {
    private final ProdutoJPA jpa;

    public ProdutoRepositorioAdapter(ProdutoJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Produto> findAll() {
        return jpa.findAll();
    }

    @Override
    public List<Produto> findByDisponibilidade(boolean disponibilidade) {
        return jpa.findByDisponibilidade(disponibilidade);
    }

    @Override
    public Optional<Produto> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return jpa.existsByNome(nome);
    }

    @Override
    public List<Produto> findByQuantidadeAtualLessThan(int quantidade) {
        return jpa.findByQuantidadeAtualLessThan(quantidade);
    }

    @Override
    public Produto save(Produto produto) {
        return jpa.save(produto);
    }

    @Override
    public List<Produto> findAllById(Set<Long> ids) {
        return jpa.findAllById(ids);
    }

    @Override
    public List<Produto> saveAll(List<Produto> produtos) {
        return jpa.saveAll(produtos);
    }

    @Override
    public void delete(Produto produto) {
        jpa.delete(produto);
    }
}