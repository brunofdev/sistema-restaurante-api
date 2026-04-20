package com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioRepositorio;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.persistencia.CardapioJPA;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CardapioJpaAdaptador implements CardapioRepositorio {

    private final CardapioJPA cardapioJpa;

    public CardapioJpaAdaptador(CardapioJPA cardapioJpa) {
        this.cardapioJpa = cardapioJpa;
    }

    @Override
    public Optional<Cardapio> findById(Long id) {
        return cardapioJpa.findById(id);
    }

    @Override
    public List<Cardapio> findAll() {
        return cardapioJpa.findAll();
    }

    @Override
    public boolean existsByNome(String nome) {
        return cardapioJpa.existsByNome(nome);
    }

    @Override
    public Cardapio save(Cardapio cardapio) {
        return cardapioJpa.save(cardapio);
    }

    @Override
    public void delete(Cardapio cardapio) {
        cardapioJpa.delete(cardapio);
    }
}