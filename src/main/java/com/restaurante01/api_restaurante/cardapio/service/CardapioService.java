package com.restaurante01.api_restaurante.cardapio.service;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioCreateDTO;
import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioNaoEncontradoException;
import com.restaurante01.api_restaurante.cardapio.mapper.CardapioMapper;
import com.restaurante01.api_restaurante.cardapio.repository.CardapioRepository;
import com.restaurante01.api_restaurante.cardapio.validator.CardapioValidator;
import com.restaurante01.api_restaurante.core.utils.FormatarString;
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
    public boolean buscarCardapioPorNome(String nome){
        return cardapioRepository.existsByNome(nome);
    }
    public Cardapio buscarCardapioPorId(Long id){
        return cardapioRepository.findById(id)
                .orElseThrow(() -> new CardapioNaoEncontradoException("Cardapio não encontrado"));
    }
    public List<CardapioDTO> listarTodosCardapios(){
        return cardapioMapper.mapearListaDeEntidadeParaDTO(cardapioRepository.findAll());
    }
    public CardapioDTO adicionarNovoCardapio(CardapioCreateDTO cardapioCreateDTO){
        cardapioValidator.validarCardapio(cardapioCreateDTO, cardapioRepository.existsByNome(FormatarString.limparEspacos(cardapioCreateDTO.getNome())));
        Cardapio novoCardapio = cardapioMapper.mapearCardapioCreateParaEntidade(cardapioCreateDTO);
        cardapioRepository.save(novoCardapio);
        return cardapioMapper.mapearUmaEntidadeParaDTO(novoCardapio);
    }
    public CardapioDTO atualizarCardapio(Long id, CardapioCreateDTO cardapioCreateDTO) {
        cardapioValidator.validarCardapio(cardapioCreateDTO, cardapioRepository.existsByNome(FormatarString.limparEspacos(cardapioCreateDTO.getNome())));
        Cardapio cardapioSalvo = cardapioRepository.findById(id)
                .map(cardapioExistente -> {
                    cardapioMapper.atualizarCampos(cardapioExistente, cardapioCreateDTO);
                    return cardapioRepository.save(cardapioExistente);
                })
                .orElseThrow(() -> new CardapioNaoEncontradoException("Cardapio não encontrado no banco"));
        return cardapioMapper.mapearUmaEntidadeParaDTO(cardapioSalvo);
    }
    public boolean deletarCardapio(Long id){
        Cardapio cardapioDeletado = cardapioRepository.findById(id)
                .orElseThrow(() -> new CardapioNaoEncontradoException("Cardápio com id " + id + " não encontrado"));
        cardapioRepository.delete(cardapioDeletado);
        return true;
    }

    public CardapioDTO listarUmCardapio(Long idCardapio) {
        return cardapioMapper.mapearUmaEntidadeParaDTO(cardapioRepository.findById(idCardapio).orElseThrow(() ->
        new CardapioNaoEncontradoException("Cardapio não encontrado")));
    }
}
