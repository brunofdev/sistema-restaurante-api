package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AssociacaoEntradaDTO {
    @NotNull(message = "ID do cardapio deve ser enviada")
    @Min(1)
    private Long idCardapio;
    @Min(1)
    @NotNull (message = "ID do produto deve ser enviada")
    private Long idProduto;
    @Min(0)
    private BigDecimal precoCustomizado;
    @Min(0)
    private Integer quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;
    

    public AssociacaoEntradaDTO(Long idCardapio, Long idProduto, BigDecimal precoCustomizado, Integer quantidadeCustomizada, String descricaoCustomizada, Boolean disponibilidadeCustomizada, String observacao) {
        this.idCardapio = idCardapio;
        this.idProduto = idProduto;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.descricaoCustomizada = descricaoCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
    }

}
