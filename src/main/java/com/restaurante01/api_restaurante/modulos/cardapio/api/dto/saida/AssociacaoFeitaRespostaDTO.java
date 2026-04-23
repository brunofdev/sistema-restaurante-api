package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AssociacaoFeitaRespostaDTO {
    private String message;
    private CardapioDTO cardapioDTO;
    private ProdutoDTO produtoDTO;
    private BigDecimal precoCustomizado;
    private Integer quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;

    public AssociacaoFeitaRespostaDTO(String message, CardapioDTO cardapioDTO, ProdutoDTO produtoDTO, BigDecimal precoCustomizado, Integer quantidadeCustomizada, Boolean disponibilidadeCustomizada, String observacao, String descricaoCustomizada) {
        this.message = message;
        this.cardapioDTO = cardapioDTO;
        this.produtoDTO = produtoDTO;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
        this.descricaoCustomizada = descricaoCustomizada;
    }

}
