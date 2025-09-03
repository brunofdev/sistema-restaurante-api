package com.restaurante01.api_restaurante.cardapioproduto.mapper;

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.mapper.CardapioMapper;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.core.mapper.AbstractMapper;
import com.restaurante01.api_restaurante.produto.dto.saida.ProdutoCustomDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.mapper.ProdutoMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardapioProdutoMapper extends AbstractMapper<CardapioProduto, CardapioProdutoDTO> {
    private final ProdutoMapper produtoMapper;
    private final CardapioMapper cardapioMapper;

    CardapioProdutoMapper( ProdutoMapper produtoMapper, CardapioMapper cardapioMapper){
        this.produtoMapper = produtoMapper;
        this.cardapioMapper = cardapioMapper;
    }

    @Override
    public CardapioProdutoDTO mapearUmaEntidadeParaDTO (CardapioProduto cardapioProduto) {
        return new CardapioProdutoDTO(
                cardapioProduto.getId(),
                cardapioMapper.mapearUmaEntidadeParaDTO(cardapioProduto.getCardapio()),
                produtoMapper.mapearUmaEntidadeParaDTO(cardapioProduto.getProduto()),
                cardapioProduto.getPrecoCustomizado(),
                cardapioProduto.getQuantidadeCustomizada(),
                cardapioProduto.getDescricaoCustomizada(),
                cardapioProduto.getDisponibilidadeCustomizada(),
                cardapioProduto.getObservacao()
        );
    }
    @Override
    public CardapioProduto mapearUmaDtoParaEntidade (CardapioProdutoDTO cardapioProdutoDTO) {
        return new CardapioProduto(
                cardapioProdutoDTO.getId(),
                cardapioMapper.mapearUmaDtoParaEntidade(cardapioProdutoDTO.getCardapio()),
                produtoMapper.mapearUmaDtoParaEntidade(cardapioProdutoDTO.getProduto()),
                cardapioProdutoDTO.getPrecoCustomizado(),
                cardapioProdutoDTO.getQuantidadeCustomizada(),
                cardapioProdutoDTO.getDisponibilidadeCustomizada(),
                cardapioProdutoDTO.getObservacao(),
                cardapioProdutoDTO.getDescricaoCustomizada()
        );
    }
    public CardapioProdutoAssociacaoRespostaDTO mapearCardapioProdutoAssociacaoDTO(CardapioProduto cardapioProduto){
        return new CardapioProdutoAssociacaoRespostaDTO(
                "O produto: " + cardapioProduto.getProduto().getNome() + " foi associado ao " + cardapioProduto.getCardapio().getNome(),
                cardapioMapper.mapearUmaEntidadeParaDTO(cardapioProduto.getCardapio()),
                produtoMapper.mapearUmaEntidadeParaDTO(cardapioProduto.getProduto()),
                cardapioProduto.getPrecoCustomizado(),
                cardapioProduto.getQuantidadeCustomizada(),
                cardapioProduto.getDisponibilidadeCustomizada(),
                cardapioProduto.getObservacao(),
                cardapioProduto.getDescricaoCustomizada()
        );
    }

    public List<CardapioComListaProdutoDTO> mapearCardapioComListaDeProduto(List<CardapioProduto> listaDeCardapioProduto){
        Map<Long, CardapioComListaProdutoDTO> map = new LinkedHashMap<>();

        for(CardapioProduto cp : listaDeCardapioProduto){
            Cardapio cardapio = cp.getCardapio();
            CardapioComListaProdutoDTO dto = map.computeIfAbsent(cardapio.getId(), id ->{
                return mapearCardapioProdutoDTO(cardapio);
        });
            Produto produto = cp.getProduto();
            ProdutoCustomDTO produtoCustomDTO = criarProdutoCustomDTO(produto, cp);
            dto.getProdutos().add(produtoCustomDTO);
    }
        return new ArrayList<>(map.values());
    }
    public CardapioProduto mapearCardapioProduto(Produto produto, Cardapio cardapio, CardapioProdutoAssociacaoEntradaDTO cardapioProdutoAssociacaoEntradaDTO){
        return new CardapioProduto(
                null,
                cardapio,
                produto,
                cardapioProdutoAssociacaoEntradaDTO.getPrecoCustomizado(),
                cardapioProdutoAssociacaoEntradaDTO.getQuantidadeCustomizada(),
                cardapioProdutoAssociacaoEntradaDTO.getDisponibilidadeCustomizada(),
                cardapioProdutoAssociacaoEntradaDTO.getObservacao(),
                cardapioProdutoAssociacaoEntradaDTO.getDescricaoCustomizada()
        );
    }
    public CardapioComListaProdutoDTO mapearCardapioProdutoDTO(Cardapio cardapio) {
        CardapioComListaProdutoDTO cardapioProdutoDTO = new CardapioComListaProdutoDTO();
        cardapioProdutoDTO.setId(cardapio.getId());
        cardapioProdutoDTO.setNome(cardapio.getNome());
        cardapioProdutoDTO.setDescricao(cardapio.getDescricao());
        cardapioProdutoDTO.setDisponibilidade(cardapio.getDisponibilidade());
        cardapioProdutoDTO.setDataInicio(cardapio.getDataInicio());
        cardapioProdutoDTO.setDataFim(cardapio.getDataFim());
        cardapioProdutoDTO.setProdutos(new ArrayList<>());
        return cardapioProdutoDTO;
    }
    public ProdutoCustomDTO criarProdutoCustomDTO (Produto produto, CardapioProduto cardapioProduto){
        ProdutoCustomDTO produtoCustomDTO = new ProdutoCustomDTO();
        produtoCustomDTO.setIdProduto(produto.getId());
        produtoCustomDTO.setNome(produto.getNome());
        produtoCustomDTO.setDescricao(produto.getDescricao());
        produtoCustomDTO.setPreco(produto.getPreco());
        produtoCustomDTO.setQuantidadeAtual(produto.getQuantidadeAtual());
        produtoCustomDTO.setDisponibilidade(produto.getDisponibilidade());
        produtoCustomDTO.setPrecoCustomizado(cardapioProduto.getPrecoCustomizado());
        produtoCustomDTO.setDescricaoCustomizada(cardapioProduto.getDescricaoCustomizada());
        produtoCustomDTO.setQuantidadeCustomizada(cardapioProduto.getQuantidadeCustomizada());
        produtoCustomDTO.setDisponibilidadeCustomizada(cardapioProduto.getDisponibilidadeCustomizada());
        produtoCustomDTO.setObservacao(cardapioProduto.getObservacao());
        return produtoCustomDTO;
    }


}
