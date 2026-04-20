package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacaoFeitaRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.associacao.mapeador.CardapioProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador.associacao.validador.CardapioProdutoValidador;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.AssociacaoNaoExisteExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarCamposCustomDaAssociacaoCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapeador mapper;
    private final CardapioProdutoValidador validator;

    public AtualizarCamposCustomDaAssociacaoCasoDeUso(CardapioProdutoRepositorio repository, CardapioProdutoMapeador mapper, CardapioProdutoValidador validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    public AssociacaoFeitaRespostaDTO executar(AssociacaoEntradaDTO dto) {
        Associacao associacaoExistente = encontrarCardapioProduto(dto.getIdCardapio(), dto.getIdProduto());
        validator.validarCardapioProdutoAssociacaoEntradaDTO(dto);
        Associacao atualizada = mapper.mapearCamposCustom(associacaoExistente, dto);
        repository.save(atualizada);
        return mapper.mapearCardapioProdutoAssociacaoDTO(atualizada);
    }


    private Associacao encontrarCardapioProduto (Long idCardapio, Long idProduto){
        return repository.findByCardapioIdAndProdutoId(idCardapio, idProduto)
                .orElseThrow(() -> new AssociacaoNaoExisteExcecao("Não existe associação entre o cardápio e o produto enviado"));
    }
}