package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;

import java.math.BigDecimal;

public record AssociacaoDTO(
        Long id,
        CardapioDTO cardapio,
        ProdutoDTO produto,
        BigDecimal precoCustomizado,
        Integer quantidadeCustomizada,
        String descricaoCustomizada,
        Boolean disponibilidadeCustomizada,
        String observacao
) {}
