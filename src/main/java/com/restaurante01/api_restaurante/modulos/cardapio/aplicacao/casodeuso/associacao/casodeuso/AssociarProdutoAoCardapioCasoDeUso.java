package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacaoFeitaRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.associacao.mapeador.CardapioProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador.associacao.validador.CardapioProdutoValidador;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.AssociacaoExistenteCardapioProdutoExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.porta.associacao.CardapioPorta;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.porta.associacao.ProdutoPorta;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssociarProdutoAoCardapioCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapeador mapper;
    private final CardapioProdutoValidador validator;
    private final CardapioPorta cardapioPorta;
    private final ProdutoPorta produtoPorta;

    public AssociarProdutoAoCardapioCasoDeUso(CardapioProdutoRepositorio repository,
                                              CardapioProdutoMapeador mapper,
                                              CardapioProdutoValidador validator,
                                              CardapioPorta cardapioPorta,
                                              ProdutoPorta produtoPorta) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.cardapioPorta = cardapioPorta;
        this.produtoPorta = produtoPorta;
    }

    @Transactional
    public AssociacaoFeitaRespostaDTO executar(AssociacaoEntradaDTO dto) {
        if(repository.existeAssociacao(dto.getIdCardapio(), dto.getIdProduto())){
            throw new AssociacaoExistenteCardapioProdutoExcecao("Associação já existe");
        };
        validator.validarCardapioProdutoAssociacaoEntradaDTO(dto);
        Produto produto = produtoPorta.obterProdutoPorId(dto.getIdProduto());
        Cardapio cardapio = cardapioPorta.buscarCardapioPorId(dto.getIdCardapio());
        Associacao novaAssociacao = mapper.mapearCardapioProduto(produto, cardapio, dto);
        repository.save(novaAssociacao);
        return mapper.mapearCardapioProdutoAssociacaoDTO(novaAssociacao);
    }
}