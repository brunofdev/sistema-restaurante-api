package com.restaurante01.api_restaurante.compartilhado.aplicacao;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;

public interface OutboxEventoHandler {
    TipoEvento tipoEvento();
    void processar(OutboxEvento outboxEvento);
}
