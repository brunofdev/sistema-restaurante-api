package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.evento.AvaliacaoConcluidaEvento;
import com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso.ProcessaPontuacaoAposAvaliacaoConcluidaCasoDeUso;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class AvaliacaoConcluidaOuvinte {

    private final OutboxRepositorio outboxRepositorio;
    private final ProcessaPontuacaoAposAvaliacaoConcluidaCasoDeUso casoDeUso;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void computaPontuacaoAposAvaliacaoConcluida(AvaliacaoConcluidaEvento evento){
        OutboxEvento outboxEvento = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.AVALIACAO, evento.avaliacaoId(), TipoEvento.COMPUTAR_PONTUACAO_AVALIACAO_REALIZADA);
        try{
            casoDeUso.executar(evento.clienteId(), evento.totalDeItensAvaliados(), evento.classificacaoAvaliacao());
            outboxEvento.processar();
        }
        catch(Exception e){
            outboxEvento.registrarFalha();
        }
        finally {
            outboxRepositorio.salvar(outboxEvento);
        }
    }
}
