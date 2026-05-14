package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.porta.AvaliacaoNotificadorPorta;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.AvaliacaoParaNotificar;
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
public class DisponibilizarAvaliacaoCasoDeUso {
    private final AvaliacaoRepositorio repositorio;
    private final AvaliacaoNotificadorPorta notificadorPorta;

    @Transactional
    public void executar(){
        LocalDateTime limite = LocalDateTime.now().minusHours(1);
        List<Avaliacao> avaliacoes = repositorio.buscarTodasCriadasAte(StatusAvaliacao.PENDENTE, limite);

        if (avaliacoes.isEmpty()) return;

        avaliacoes.forEach(avaliacao -> {
            try {
                avaliacao.mudarStatusAvaliacao(StatusAvaliacao.DISPONIVEL);
                avaliacao.foiEnviadaAoCliente();
                notificadorPorta.notificarCliente(new AvaliacaoParaNotificar(avaliacao.getId(), avaliacao.getClienteId(), avaliacao.getPedidoId()));
            }
            catch (Exception e){
                log.error("Erro ao notificar avaliacao id={}", avaliacao.getId(), e);
            }
        });
        repositorio.salvarLista(avaliacoes);
    }
}


