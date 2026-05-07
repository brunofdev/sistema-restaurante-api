package com.restaurante01.api_restaurante.compartilhado.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.StatusOutbox;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.OutboxNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxJpaAdaptador implements OutboxRepositorio {

    private final OutboxJPA jpa;

    public OutboxJpaAdaptador(OutboxJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(OutboxEvento outboxEvento) {
        jpa.save(outboxEvento);
    }

    @Override
    public OutboxEvento buscarPorAgregadoEIdAgregadoETipoEvento(Agregado agregado, Long idAgregado, TipoEvento tipoEvento){
        return jpa.findByAgregadoTipoAndAgregadoIdAndTipo(agregado, idAgregado, tipoEvento).orElseThrow(() -> new OutboxNaoEncontradoExcecao("Outbox não encontrado para o evento solicitado"));
    }

    @Override
    public List<OutboxEvento> buscarPendentes(){
        return jpa.findByStatus(StatusOutbox.PENDENTE);
    }

}

