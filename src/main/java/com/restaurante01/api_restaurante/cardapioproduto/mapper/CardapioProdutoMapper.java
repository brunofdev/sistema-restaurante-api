package com.restaurante01.api_restaurante.cardapioproduto.mapper;

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.produto.dto.ProdutoCustomDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardapioProdutoMapper {

    public CardapioProdutoDTO mapearCardapio(Cardapio cardapio){
        CardapioProdutoDTO cardapioProdutoDTO = new CardapioProdutoDTO();
        cardapioProdutoDTO.setId(cardapio.getId());
        cardapioProdutoDTO.setNome(cardapio.getNome());
        cardapioProdutoDTO.setDescricao(cardapio.getDescricao());
        cardapioProdutoDTO.setDisponibilidade(cardapio.getDisponibilidade());
        cardapioProdutoDTO.setDataInicio(cardapio.getDataInicio());
        cardapioProdutoDTO.setDataFim(cardapio.getDataFim());
        cardapioProdutoDTO.setProdutos(new ArrayList<>());
        return cardapioProdutoDTO;
    }
    public ProdutoCustomDTO mapearProdutoCustomDTO (Produto produto, CardapioProduto cardapioProduto){
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
    public List<CardapioProdutoDTO> mapearCardapioComProduto (List<CardapioProduto> listaDeCardapioProduto){
        Map<Long, CardapioProdutoDTO> map = new LinkedHashMap<>();

        for(CardapioProduto cp : listaDeCardapioProduto){
            Cardapio cardapio = cp.getCardapio();
            CardapioProdutoDTO dto = map.computeIfAbsent(cardapio.getId(), id ->{
                return mapearCardapio(cardapio);
        });
            Produto produto = cp.getProduto();
            ProdutoCustomDTO produtoCustomDTO = mapearProdutoCustomDTO(produto, cp);
            dto.getProdutos().add(produtoCustomDTO);
    }
        return new ArrayList<>(map.values());

    }
}
