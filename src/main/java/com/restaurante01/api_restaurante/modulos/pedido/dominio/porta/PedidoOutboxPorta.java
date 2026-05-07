package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;

public interface PedidoOutboxPorta {
    void guardarEvento(Agregado agregado, Long agregadoId,
                       TipoEvento tipoEvento, String payload)
            throws JsonProcessingException;
}