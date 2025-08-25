package com.restaurante01.api_restaurante.cardapioproduto.mapper;

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.cardapioproduto.factory.CardapioProdutoFactory;
import com.restaurante01.api_restaurante.core.mapper.AbstractMapper;
import com.restaurante01.api_restaurante.produto.dto.ProdutoCustomDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardapioProdutoMapper extends AbstractMapper<CardapioProduto, CardapioProdutoDTO> {
    private final CardapioProdutoFactory cardapioProdutoFactory;

    CardapioProdutoMapper( CardapioProdutoFactory cardapioProdutoFactory){
        this.cardapioProdutoFactory = cardapioProdutoFactory;
    }

    @Override
    public CardapioProdutoDTO mapearUmaEntidadeParaDTO (CardapioProduto cardapioProduto) {
        return new CardapioProdutoDTO(
                cardapioProduto.getId(),
                cardapioProduto.getCardapio(),
                cardapioProduto.getProduto(),
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
                cardapioProdutoDTO.getCardapio(),
                cardapioProdutoDTO.getProduto(),
                cardapioProdutoDTO.getPrecoCustomizado(),
                cardapioProdutoDTO.getQuantidadeCustomizada(),
                cardapioProdutoDTO.getDisponibilidadeCustomizada(),
                cardapioProdutoDTO.getObservacao(),
                cardapioProdutoDTO.getDescricaoCustomizada()
        );
    }

    public List<CardapioComListaProdutoDTO> mapearCardapioComListaDeProduto(List<CardapioProduto> listaDeCardapioProduto){
        Map<Long, CardapioComListaProdutoDTO> map = new LinkedHashMap<>();

        for(CardapioProduto cp : listaDeCardapioProduto){
            Cardapio cardapio = cp.getCardapio();
            CardapioComListaProdutoDTO dto = map.computeIfAbsent(cardapio.getId(), id ->{
                return cardapioProdutoFactory.criarCardapioProdutoDTO(cardapio);
        });
            Produto produto = cp.getProduto();
            ProdutoCustomDTO produtoCustomDTO = cardapioProdutoFactory.criarProdutoCustomDTO(produto, cp);
            dto.getProdutos().add(produtoCustomDTO);
    }
        return new ArrayList<>(map.values());
    }


}
