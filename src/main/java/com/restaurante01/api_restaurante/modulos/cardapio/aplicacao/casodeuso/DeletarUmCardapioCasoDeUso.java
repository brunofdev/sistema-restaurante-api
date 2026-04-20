package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class DeletarUmCardapioCasoDeUso {

    private final CardapioRepositorio cardapioRepositorio;

    public DeletarUmCardapioCasoDeUso(CardapioRepositorio cardapioRepositorio) {
        this.cardapioRepositorio = cardapioRepositorio;
    }

    public void executar(Long id) {
        Cardapio cardapio = cardapioRepositorio.findById(id)
                .orElseThrow(() -> new CardapioNaoEncontradoExcecao("Cardápio com id " + id + " não encontrado"));
        cardapioRepositorio.delete(cardapio);
    }
}
