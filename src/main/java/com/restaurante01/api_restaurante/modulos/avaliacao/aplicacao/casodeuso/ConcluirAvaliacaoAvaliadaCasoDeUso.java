package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.ResponderAvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.evento.AvaliacaoConcluidaEvento;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.payload.AvaliacaoFidelidadePayload;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.ClassificacaoAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.RespostaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.Map;
import java.util.Objects;


@Service
@AllArgsConstructor
public class ConcluirAvaliacaoAvaliadaCasoDeUso {
    private final AvaliacaoRepositorio repositorio;
    private final AvaliacaoMapeador mapeador;
    private final ApplicationEventPublisher publicadorDeEvento;
    private final OutboxRepositorio outboxRepositorio;
    private final ObjectMapper objectMapper;

    @Transactional
    public void executar(Long clienteId, ResponderAvaliacaoDTO dto) throws JsonProcessingException {
        Avaliacao avaliacao = encontrarAvaliacao(dto.idAvaliacao());
        verificaSeClienteDonoDaAvaliacao(clienteId, avaliacao);
        verificaSeAvaliacaoDisponivel(avaliacao);
        RespostaAvaliacao avaliacaoGeral = dto.avaliacaoDTO() != null ? mapeador.mapearRespostaAvaliacao(dto.avaliacaoDTO().nota(), dto.avaliacaoDTO().comentario()) : new RespostaAvaliacao(null, null);
        Map<Long, RespostaAvaliacao> itensAvaliados = dto.itensAvaliados() != null ? mapeador.mapearListaItensAvaliados(dto.itensAvaliados()) : Collections.emptyMap();
        avaliacao.concluirAvaliacao(avaliacaoGeral, itensAvaliados);
        repositorio.salvar(avaliacao);
        publicarEvento(avaliacao.getId(), clienteId, avaliacao.getAvaliacao(), itensAvaliados.size());
    }
    private Avaliacao encontrarAvaliacao(Long id){
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new AvaliacaoInvalidaExcecao("Avaliação com id " + id + " não foi localizada"));
    }
    private void verificaSeAvaliacaoDisponivel(Avaliacao avaliacao){
        if(avaliacao.getStatus() != StatusAvaliacao.DISPONIVEL){
            throw new AvaliacaoInvalidaExcecao("Só é possível concluir uma avaliação com status DISPONIVEL.");
        }
    }
    private void verificaSeClienteDonoDaAvaliacao(Long idCliente, Avaliacao avaliacao){
        if(!Objects.equals(avaliacao.getClienteId(), idCliente)){
            throw new AvaliacaoInvalidaExcecao("Cliente não é o dono desta avaliacao");
        }
    }

    private void publicarEvento(Long idAvaliacao, Long clienteId, ClassificacaoAvaliacao classificacaoAvaliacao, int totalDeItensAvaliados) throws JsonProcessingException {
        if(classificacaoAvaliacao != ClassificacaoAvaliacao.NAO_AVALIADO || totalDeItensAvaliados > 0) {
            registrarOutbox(idAvaliacao, clienteId, classificacaoAvaliacao, totalDeItensAvaliados);
            publicadorDeEvento.publishEvent(new AvaliacaoConcluidaEvento(clienteId, classificacaoAvaliacao, totalDeItensAvaliados));
        }
    }
    private void registrarOutbox(Long idAvaliacao, Long clienteId, ClassificacaoAvaliacao classificacaoAvaliacao, int totalItensAvaliados) throws JsonProcessingException {
        AvaliacaoFidelidadePayload avaliacaoFidelidadePayload = new AvaliacaoFidelidadePayload(clienteId, classificacaoAvaliacao, totalItensAvaliados);
        outboxRepositorio.salvar(OutboxEvento.criar(Agregado.AVALIACAO, idAvaliacao, TipoEvento.COMPUTAR_PONTUACAO_AVALIACAO_REALIZADA, objectMapper.writeValueAsString(avaliacaoFidelidadePayload)));
    }

}

