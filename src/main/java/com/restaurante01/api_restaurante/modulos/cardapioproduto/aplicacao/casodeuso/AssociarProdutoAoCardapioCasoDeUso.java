package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.mapeador.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.validador.CardapioProdutoValidator;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoExistenteCardapioProdutoException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.porta.CardapioPorta;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.porta.ProdutoPorta;
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
    private final CardapioPorta cardapioPorta;
    private final ProdutoPorta produtoPorta;

    public AssociarProdutoAoCardapioCasoDeUso(CardapioProdutoRepositorio repository,
                                              CardapioProdutoMapper mapper,
                                              CardapioProdutoValidator validator,
                                              CardapioPorta cardapioPorta,
                                              ProdutoPorta produtoPorta) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.cardapioPorta = cardapioPorta;
        this.produtoPorta = produtoPorta;
    }

    @Transactional
    public CardapioProdutoAssociacaoRespostaDTO executar(CardapioProdutoAssociacaoEntradaDTO dto) {
        if(repository.existeAssociacao(dto.getIdCardapio(), dto.getIdProduto())){
            throw new AssociacaoExistenteCardapioProdutoException("Associação já existe");
        };
        validator.validarCardapioProdutoAssociacaoEntradaDTO(dto);
        Produto produto = produtoPorta.obterProdutoPorId(dto.getIdProduto());
        Cardapio cardapio = cardapioPorta.buscarCardapioPorId(dto.getIdCardapio());
        CardapioProduto novaAssociacao = mapper.mapearCardapioProduto(produto, cardapio, dto);
        repository.save(novaAssociacao);
        return mapper.mapearCardapioProdutoAssociacaoDTO(novaAssociacao);
    }
}