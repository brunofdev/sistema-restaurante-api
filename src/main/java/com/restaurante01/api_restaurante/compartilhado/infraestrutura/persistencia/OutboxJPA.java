package com.restaurante01.api_restaurante.compartilhado.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.StatusOutbox;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OutboxJPA extends JpaRepository<OutboxEvento, Long>{
    Optional<OutboxEvento> findByAgregadoTipoAndAgregadoIdAndTipo(Agregado agregadoTipo, Long agregadoId, TipoEvento tipoEvento);
    List<OutboxEvento> findByStatus(StatusOutbox status);
}

