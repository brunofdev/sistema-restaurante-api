package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.TentativaNotificacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepositorio {
    Avaliacao salvar(Avaliacao avaliacao);
    List<Avaliacao> salvarLista(List<Avaliacao> avaliacoes);
    Avaliacao atualizar(Avaliacao avaliacao);
    Optional<Avaliacao> buscarPorId(Long idAvaliacao);
    Optional<Avaliacao> buscarPorPedidoId(Long idPedido);
    List<Avaliacao> buscarTodos();
    List<Avaliacao> buscarExpiradas(StatusAvaliacao status, LocalDateTime horarioAgora);
    List<Avaliacao> buscarTodasCriadasAte(StatusAvaliacao status, LocalDateTime horario);
    List<Avaliacao> buscarPendentesDeRenotificacao(StatusAvaliacao status, TentativaNotificacao TentativaNotificacao, LocalDateTime horario);
    List<Avaliacao> buscarAvaliacoesPorClienteId(Long idCliente);
}
