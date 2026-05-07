package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;

import java.math.BigDecimal;

public record AssociacaoFeitaRespostaDTO(
        String message,
        CardapioDTO cardapioDTO,
        ProdutoDTO produtoDTO,
        BigDecimal precoCustomizado,
        Integer quantidadeCustomizada,
        Boolean disponibilidadeCustomizada,
        String observacao,
        String descricaoCustomizada
) {}
