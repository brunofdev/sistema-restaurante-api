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

    public List<CardapioProdutoDTO> mapearEntidadeComDTO (List<CardapioProduto> lista){
        Map<Long, CardapioProdutoDTO> map = new LinkedHashMap<>();

        for(CardapioProduto cp : lista){
            Cardapio cardapio = cp.getCardapio();
            CardapioProdutoDTO dto = map.computeIfAbsent(cardapio.getId(), id ->{
                CardapioProdutoDTO c = new CardapioProdutoDTO();
                c.setId(cardapio.getId());
                c.setNome(cardapio.getNome());
                c.setDescricao(cardapio.getDescricao());
                c.setDisponibilidade(cardapio.getDisponibilidade());
                c.setDataInicio(cardapio.getDataInicio());
                c.setDataFim(cardapio.getDataFim());
                c.setProdutos(new ArrayList<>());
                return c;
        });
            Produto produto = cp.getProduto();
            ProdutoCustomDTO produtoCustomDTO = new ProdutoCustomDTO();
            produtoCustomDTO.setIdProduto(produto.getId());
            produtoCustomDTO.setNome(produto.getNome());
            produtoCustomDTO.setDescricao(produto.getDescricao());
            produtoCustomDTO.setPreco(produto.getPreco());
            produtoCustomDTO.setQuantidadeAtual(produto.getQuantidadeAtual());
            produtoCustomDTO.setDisponibilidade(produto.getDisponibilidade());

            produtoCustomDTO.setPrecoCustomizado(cp.getPrecoCustomizado());
            produtoCustomDTO.setDescricaoCustomizada(cp.getDescricaoCustomizada());
            produtoCustomDTO.setQuantidadeCustomizada(cp.getQuantidadeCustomizada());
            produtoCustomDTO.setDisponibilidadeCustomizada(cp.getDisponibilidadeCustomizada());
            produtoCustomDTO.setObservacao(cp.getObservacao());

            dto.getProdutos().add(produtoCustomDTO);
    }
        return new ArrayList<>(map.values());

    }
}
