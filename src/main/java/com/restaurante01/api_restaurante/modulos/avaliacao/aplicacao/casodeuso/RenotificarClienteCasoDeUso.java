package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.TentativaNotificacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.AvaliacaoParaNotificar;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.porta.AvaliacaoNotificadorPorta;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RenotificarClienteCasoDeUso {

    private final AvaliacaoRepositorio repositorio;
    private final AvaliacaoNotificadorPorta notificador;

    @Transactional
    public void executar(){
    renotificarAvaliacao(TentativaNotificacao.PRIMEIRA_TENTATIVA, 3);
    renotificarAvaliacao(TentativaNotificacao.SEGUNDA_TENTATIVA, 6);

    }

    private void renotificarAvaliacao(TentativaNotificacao tentativa, int diasParaNovaTentativa) {
        List<Avaliacao> avaliacoes = repositorio.buscarPendentesDeRenotificacao(
                StatusAvaliacao.DISPONIVEL,
                tentativa,
                LocalDateTime.now().minusDays(diasParaNovaTentativa)
        );

        avaliacoes.forEach(avaliacao -> {
            try {
                avaliacao.foiEnviadaAoCliente();
                notificador.notificarCliente(new AvaliacaoParaNotificar(avaliacao.getId(), avaliacao.getClienteId(), avaliacao.getPedidoId()));
            }
            catch (Exception e) {
                log.error("Erro ao notificar avaliacao id={}", avaliacao.getId(), e);
            }
            });
        repositorio.salvarLista(avaliacoes);
    }
}
