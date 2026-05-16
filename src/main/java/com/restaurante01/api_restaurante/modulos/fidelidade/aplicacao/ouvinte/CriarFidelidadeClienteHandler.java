package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso.CriarFidelidadeParaClienteCasoDeUso;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CriarFidelidadeClienteHandler implements OutboxEventoHandler {

    private final CriarFidelidadeParaClienteCasoDeUso casoDeUso;

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.CRIAR_FIDELIDADE_CLIENTE;
    }

    @Override
    public void processar(OutboxEvento outboxEvento) {
        try {
            casoDeUso.executar(outboxEvento.getAgregadoId());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao reprocessar outbox id=" + outboxEvento.getId(), e);
        }
    }
}
