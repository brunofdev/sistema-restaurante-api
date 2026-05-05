package com.restaurante01.api_restaurante.compartilhado.infraestrutura.adaptador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoOutboxPorta;
import org.springframework.stereotype.Service;

@Service
public class OutboxAdaptador implements PedidoOutboxPorta {


    private final OutboxRepositorio repositorio;

    public OutboxAdaptador(OutboxRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void guardarEvento(Agregado agregado, Long agregadoId,
                                            TipoEvento tipoEvento, String payload) throws JsonProcessingException {
        OutboxEvento outboxEvento = OutboxEvento.criar(
                agregado,
                agregadoId,
                tipoEvento,
                payload);
        repositorio.salvar(outboxEvento);
    }
}
