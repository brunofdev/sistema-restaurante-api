package com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioRepositorio;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.persistencia.CardapioRepositorioJpa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CardapioRepositorioAdaptador implements CardapioRepositorio {

    private final CardapioRepositorioJpa cardapioRepositorioJpa;

    public CardapioRepositorioAdaptador(CardapioRepositorioJpa cardapioRepositorioJpa) {
        this.cardapioRepositorioJpa = cardapioRepositorioJpa;
    }

    @Override
    public Optional<Cardapio> findById(Long id) {
        return cardapioRepositorioJpa.findById(id);
    }

    @Override
    public List<Cardapio> findAll() {
        return cardapioRepositorioJpa.findAll();
    }

    @Override
    public boolean existsByNome(String nome) {
        return cardapioRepositorioJpa.existsByNome(nome);
    }

    @Override
    public Cardapio save(Cardapio cardapio) {
        return cardapioRepositorioJpa.save(cardapio);
    }

    @Override
    public void delete(Cardapio cardapio) {
        cardapioRepositorioJpa.delete(cardapio);
    }
}