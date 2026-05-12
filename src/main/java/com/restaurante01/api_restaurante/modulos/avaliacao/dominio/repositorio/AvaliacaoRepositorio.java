package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepositorio {
    Avaliacao salvar(Avaliacao avaliacao);
    Avaliacao atualizar(Avaliacao avaliacao);
    Optional<Avaliacao> buscarPorId(Long idAvaliacao);
    Optional<Avaliacao> buscarPorPedidoId(Long idPedido);
    List<Avaliacao> buscarTodos();

}
