package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso.CriarFidelidadeParaClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.evento.ClienteCriadoEvento;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class ClienteCriadoOuvinte {

    private final OutboxRepositorio outboxRepositorio;
    private final CriarFidelidadeParaClienteCasoDeUso casoDeUso;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quandoClienteCriado(ClienteCriadoEvento evento) {
        OutboxEvento outboxEvento = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(
                Agregado.CLIENTE, evento.clienteId(), TipoEvento.CRIAR_FIDELIDADE_CLIENTE);
        try {
            casoDeUso.executar(evento.clienteId());
            outboxEvento.processar();
        } catch (Exception e) {
            outboxEvento.registrarFalha();
        } finally {
            outboxRepositorio.salvar(outboxEvento);
        }
    }
}
