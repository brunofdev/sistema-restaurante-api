package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoAvaliacaoPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriarAvaliacaoCasoDeUso {

    private final AvaliacaoRepositorio repositorio;
    private final AvaliacaoMapeador mapeador;

    public CriarAvaliacaoCasoDeUso(AvaliacaoRepositorio repositorio, AvaliacaoMapeador mapeador) {
        this.repositorio = repositorio;
        this.mapeador = mapeador;
    }

    public void executar(Long pedidoId, Long clienteId, List<ItemPedidoAvaliacaoPayload> itensPedido){
        Avaliacao avaliacao = Avaliacao.criar(pedidoId, clienteId, mapeador.mapearItensPedido(itensPedido));
        repositorio.salvar(avaliacao);
    }
}
