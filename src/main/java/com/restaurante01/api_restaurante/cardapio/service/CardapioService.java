package com.restaurante01.api_restaurante.cardapio.service;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioNaoEncontradoException;
import com.restaurante01.api_restaurante.cardapio.mapper.CardapioMapper;
import com.restaurante01.api_restaurante.cardapio.repository.CardapioRepository;
import com.restaurante01.api_restaurante.cardapio.validator.CardapioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardapioService {
    private final CardapioRepository cardapioRepository;
    private final CardapioMapper cardapioMapper;
    private final CardapioValidator cardapioValidator;

    @Autowired
    public CardapioService(CardapioRepository cardapioRepository, CardapioMapper cardapioMapper, CardapioValidator cardapioValidator){
        this.cardapioRepository = cardapioRepository;
        this.cardapioMapper = cardapioMapper;
        this.cardapioValidator = cardapioValidator;
    }
    public List<CardapioDTO> listarTodosCardapios(){
        return cardapioMapper.mapearListaDeEntidadeParaDTO(cardapioRepository.findAll());
    }
    public CardapioDTO adicionarNovoCardapio(CardapioDTO cardapioDTO){
        cardapioValidator.validarCardapio(cardapioDTO);
        Cardapio novoCardapio = cardapioMapper.mapearUmaDtoParaEntidade(cardapioDTO);
        cardapioRepository.save(novoCardapio);
        return cardapioMapper.mapearUmaEntidadeParaDTO(novoCardapio);
    }
    public CardapioDTO atualizarCardapio(Long id, CardapioDTO cardapioDTO) {
        cardapioValidator.validarCardapio(cardapioDTO);
        /**esta parte do método faz: Encontra um cardapio com base em um ID ->
        **Depois atualizada os campos utilizando um método da classe mapper ->
        **Depois salva o cardapio atualizado, ou gera uma exception*/
        Cardapio cardapioSalvo = cardapioRepository.findById(id)
                .map(cardapioExistente -> {
                    cardapioMapper.atualizarCampos(cardapioExistente, cardapioDTO);
                    return cardapioRepository.save(cardapioExistente);
                })
                .orElseThrow(() -> new CardapioNaoEncontradoException("Cardapio não encontrado no banco"));
        return cardapioMapper.mapearUmaEntidadeParaDTO(cardapioSalvo);
    }
    public CardapioDTO deletarCardapio(Long id){
        Cardapio cardapioDeletado = cardapioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cardápio com id " + id + " não encontrado"));
        cardapioRepository.delete(cardapioDeletado);
        return cardapioMapper.mapearUmaEntidadeParaDTO(cardapioDeletado);
    }
}
