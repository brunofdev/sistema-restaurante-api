package com.restaurante01.api_restaurante.modulos.avaliacao.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AvaliacaoJPA extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao>  findByPedidoId(Long idPedido);
    List<Avaliacao>  findByStatusAndDataExpiracaoBefore(StatusAvaliacao status, LocalDateTime dataExpiracao);
    List<Avaliacao> findByStatusAndDataCriacaoBefore(StatusAvaliacao status, LocalDateTime dataCriacao);}
