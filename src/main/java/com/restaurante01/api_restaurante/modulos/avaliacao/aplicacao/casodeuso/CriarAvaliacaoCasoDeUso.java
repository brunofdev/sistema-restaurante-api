package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import org.springframework.stereotype.Service;

@Service
public class CriarAvaliacaoCasoDeUso {

    private final AvaliacaoRepositorio repositorio;

    public CriarAvaliacaoCasoDeUso(AvaliacaoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void executar(Long pedidoId, Long clienteId){
        Avaliacao avaliacao = Avaliacao.criar(pedidoId, clienteId);
        repositorio.salvar(avaliacao);
    }
}
