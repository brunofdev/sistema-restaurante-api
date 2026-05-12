package com.restaurante01.api_restaurante.modulos.avaliacao.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoJPA extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao>  findByPedidoId(Long idPedido);
}
