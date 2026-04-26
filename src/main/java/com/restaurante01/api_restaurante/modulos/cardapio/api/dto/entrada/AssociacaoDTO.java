package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AssociacaoDTO {
    private Long id;
    private CardapioDTO cardapio;
    private ProdutoDTO produto;
    private BigDecimal precoCustomizado;
    private Integer quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;

    public AssociacaoDTO() {
    }

    public AssociacaoDTO(Long id, CardapioDTO cardapio, ProdutoDTO produto, BigDecimal precoCustomizado,
                         Integer quantidadeCustomizada, String descricaoCustomizada,
                         Boolean disponibilidadeCustomizada, String observacao) {
        this.id = id;
        this.cardapio = cardapio;
        this.produto = produto;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.descricaoCustomizada = descricaoCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
    }

}
