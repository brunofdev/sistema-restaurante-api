package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.BuscarCardapioPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.mapeador.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.validador.CardapioProdutoValidator;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.ObterProdutoPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssociarProdutoAoCardapioCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapper mapper;
    private final CardapioProdutoValidator validator;
    private final BuscarCardapioPorIdCasoDeUso buscarCardapioPorId;
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorId;

    public AssociarProdutoAoCardapioCasoDeUso(CardapioProdutoRepositorio repository,
                                              CardapioProdutoMapper mapper,
                                              CardapioProdutoValidator validator,
                                              BuscarCardapioPorIdCasoDeUso buscarCardapioPorId,
                                              ObterProdutoPorIdCasoDeUso obterProdutoPorId) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.buscarCardapioPorId = buscarCardapioPorId;
        this.obterProdutoPorId = obterProdutoPorId;
    }

    @Transactional
    public CardapioProdutoAssociacaoRespostaDTO executar(CardapioProdutoAssociacaoEntradaDTO dto) {
        boolean existe = repository.existeAssociacao(dto.getIdCardapio(), dto.getIdProduto());
        validator.validarCardapioProdutoAssociacaoEntradaDTO(dto, existe, false);

        Produto produto = obterProdutoPorId.retornarEntidade(dto.getIdProduto());
        Cardapio cardapio = buscarCardapioPorId.executar(dto.getIdCardapio());

        CardapioProduto novaAssociacao = mapper.mapearCardapioProduto(produto, cardapio, dto);
        repository.save(novaAssociacao);

        return mapper.mapearCardapioProdutoAssociacaoDTO(novaAssociacao);
    }
}