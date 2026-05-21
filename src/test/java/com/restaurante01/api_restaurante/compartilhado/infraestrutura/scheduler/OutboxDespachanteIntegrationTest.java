package com.restaurante01.api_restaurante.compartilhado.infraestrutura.scheduler;

import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.GatilhoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.compartilhado.infraestrutura.persistencia.OutboxJpaAdaptador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({OutboxJpaAdaptador.class, OutboxDespachante.class})
class OutboxDespachanteIntegrationTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        CapturadorDeOrdem capturadorDeOrdem() {
            return new CapturadorDeOrdem();
        }
    }

    static class CapturadorDeOrdem implements OutboxEventoHandler {

        private final List<Long> idsProcessados = new ArrayList<>();

        @Override
        public TipoEvento tipoEvento() {
            return TipoEvento.COMPUTAR_PONTUACAO_PEDIDO_ENTREGUE;
        }

        @Override
        public void processar(OutboxEvento evento) {
            idsProcessados.add(evento.getId());
        }

        public List<Long> idsProcessados() {
            return idsProcessados;
        }
    }

    @Autowired
    private OutboxRepositorio outboxRepositorio;

    @Autowired
    private OutboxDespachante outboxDespachante;

    @Autowired
    private CapturadorDeOrdem capturador;

    @BeforeEach
    void limpar() {
        capturador.idsProcessados().clear();
    }

    @Test
    @DisplayName("reprocessarPendentes deve processar eventos do mais antigo para o mais recente baseado em criadoEm")
    void reprocessarPendentes_deveProcessarDoMaisAntigoParaOMaisRecente() throws Exception {
        LocalDateTime agora = LocalDateTime.now();

        // Inserção deliberadamente fora de ordem cronológica
        OutboxEvento eventoMaisRecente   = criarComData(Agregado.PEDIDO, 1L, agora);
        OutboxEvento eventoMaisAntigo    = criarComData(Agregado.PEDIDO, 2L, agora.minusHours(3));
        OutboxEvento eventoIntermediario = criarComData(Agregado.PEDIDO, 3L, agora.minusHours(1));

        outboxRepositorio.salvar(eventoMaisRecente);
        outboxRepositorio.salvar(eventoMaisAntigo);
        outboxRepositorio.salvar(eventoIntermediario);

        outboxDespachante.reprocessarPendentes();

        assertThat(capturador.idsProcessados())
                .as("eventos devem ser processados do mais antigo (criadoEm menor) para o mais recente")
                .hasSize(3)
                .containsExactly(
                        eventoMaisAntigo.getId(),
                        eventoIntermediario.getId(),
                        eventoMaisRecente.getId()
                );
    }

    private OutboxEvento criarComData(Agregado agregado, Long agregadoId, LocalDateTime criadoEm) throws Exception {
        OutboxEvento evento = OutboxEvento.criar(
                agregado,
                agregadoId,
                GatilhoEvento.PEDIDO_ENTREGUE,
                TipoEvento.COMPUTAR_PONTUACAO_PEDIDO_ENTREGUE,
                "{}"
        );
        Field field = OutboxEvento.class.getDeclaredField("criadoEm");
        field.setAccessible(true);
        field.set(evento, criadoEm);
        return evento;
    }
}
