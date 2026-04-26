package com.restaurante01.api_restaurante.modulos.produto.infraestrutura.persistencia;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoJPA extends JpaRepository<Produto, Long> {

    List<Produto> findByDisponibilidade(boolean disponibilidade);
    List<Produto> findByQuantidadeAtualLessThan(Integer quantidade);
    boolean existsByNome(String nome);
    Produto findByNome(String nomeProduto);
}
