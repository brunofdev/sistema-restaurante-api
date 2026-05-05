package com.restaurante01.api_restaurante.compartilhado.dominio.repositorio;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;

import java.util.List;
import java.util.Optional;

public interface OutboxRepositorio {
    void salvar(OutboxEvento outboxEvento);
    OutboxEvento buscarPorAgregadoEIdAgregado(Agregado agregado, Long idAgregado);
    List<OutboxEvento> buscarPendentes();
}
