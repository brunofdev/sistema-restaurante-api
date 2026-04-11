package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.mapeador.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.validador.CardapioProdutoValidator;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarCamposCustomDaAssociacaoCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapper mapper;
    private final CardapioProdutoValidator validator;

    public AtualizarCamposCustomDaAssociacaoCasoDeUso(CardapioProdutoRepositorio repository, CardapioProdutoMapper mapper, CardapioProdutoValidator validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    public CardapioProdutoAssociacaoRespostaDTO executar(CardapioProdutoAssociacaoEntradaDTO dto) {
        CardapioProduto associacaoExistente = encontrarCardapioProduto(dto.getIdCardapio(), dto.getIdProduto());
        boolean existe = true;
        validator.validarCardapioProdutoAssociacaoEntradaDTO(dto, existe, true);
        CardapioProduto atualizada = mapper.mapearCamposCustom(associacaoExistente, dto);
        repository.save(atualizada);
        return mapper.mapearCardapioProdutoAssociacaoDTO(atualizada);
    }


    private CardapioProduto encontrarCardapioProduto (Long idCardapio, Long idProduto){
        return repository.findByCardapioIdAndProdutoId(idCardapio, idProduto)
                .orElseThrow(() -> new AssociacaoNaoExisteException("Não existe associação entre o cardápio e o produto enviado"));
    }
}