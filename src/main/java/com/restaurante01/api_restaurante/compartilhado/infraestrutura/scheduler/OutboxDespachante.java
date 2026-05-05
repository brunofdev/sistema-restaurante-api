package com.restaurante01.api_restaurante.compartilhado.infraestrutura.scheduler;

import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OutboxDespachante {

    private final OutboxRepositorio outboxRepositorio;
    private final Map<TipoEvento, OutboxEventoHandler> handlers;


    public OutboxDespachante(OutboxRepositorio outboxRepositorio,
                            List<OutboxEventoHandler> handlers) {
        this.outboxRepositorio = outboxRepositorio;
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(OutboxEventoHandler::tipoEvento, h -> h));
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void reprocessarPendentes() {
        outboxRepositorio.buscarPendentes().forEach(evento -> {
            OutboxEventoHandler handler = handlers.get(evento.getTipo());
            if (handler == null) {
                log.warn("Sem handler para tipo {}", evento.getTipo());
                return;
            }
            try {
                handler.processar(evento);
                evento.processar();
            } catch (Exception e) {
                evento.registrarFalha();
            }
            outboxRepositorio.salvar(evento);
        });
    }
}