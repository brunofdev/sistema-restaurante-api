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
        CardapioDTO cardapioDTO = cardapioMapper.mapearCardapioCreateParaCardapioDTO(cardapioCreateDTO);
        cardapioValidator.validarCardapio(cardapioDTO, cardapioRepository.existsByNome(FormatarString.limparEspacos(cardapioDTO.getNome())));
        Cardapio novoCardapio = cardapioMapper.mapearCardapioCreateParaEntidade(cardapioCreateDTO);
        cardapioRepository.save(novoCardapio);
        return cardapioMapper.mapearUmaEntidadeParaDTO(novoCardapio);
    }
    /* AJUSTAR
    public CardapioDTO atualizarCardapio(CardapioDTO cardapioDTO) {
        cardapioValidator.validarCardapio(cardapioDTO, cardapioRepository.existsByNome(FormatarString.limparEspacos(cardapioDTO.getNome())));
        Cardapio cardapioSalvo = cardapioRepository.findById(cardapioDTO.getId())
                .map(cardapioExistente -> {
                    cardapioMapper.atualizarCampos(cardapioExistente, cardapioDTO);
                    return cardapioRepository.save(cardapioExistente);
                })
                .orElseThrow(() -> new CardapioNaoEncontradoException("Cardapio não encontrado no banco"));
        return cardapioMapper.mapearUmaEntidadeParaDTO(cardapioSalvo);
    }
     */
    public void deletarCardapio(Long id){
        Cardapio cardapioDeletado = cardapioRepository.findById(id)
                .orElseThrow(() -> new CardapioNaoEncontradoException("Cardápio com id " + id + " não encontrado"));
        cardapioRepository.delete(cardapioDeletado);
    }

    public CardapioDTO listarUmCardapio(Long idCardapio) {
        return cardapioMapper.mapearUmaEntidadeParaDTO(cardapioRepository.findById(idCardapio).orElseThrow(() ->
        new CardapioNaoEncontradoException("Cardapio não encontrado")));
    }
}
