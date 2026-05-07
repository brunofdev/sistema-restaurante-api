package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.saida.ProdutoCustomDTO;

import java.time.LocalDate;
import java.util.List;

public record AssociacoesDTO(
        Long id,
        String nome,
        String descricao,
        Boolean disponibilidade,
        LocalDate dataInicio,
        LocalDate dataFim,
        List<ProdutoCustomDTO> produtos
) {}
