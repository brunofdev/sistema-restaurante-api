package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioNaoEncontradoException;
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
                .orElseThrow(() -> new CardapioNaoEncontradoException("Cardápio com id " + id + " não encontrado"));
        cardapioRepositorio.delete(cardapio);
    }
}
