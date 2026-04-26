package com.restaurante01.api_restaurante.modulos.produto.api.dto.saida;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoCustomDTO {
    private Long idProduto;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidadeAtual;
    private Boolean disponibilidade;
    private BigDecimal precoCustomizado;
    private int quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;

    public ProdutoCustomDTO(){}
    public ProdutoCustomDTO(Long idProduto, String nome, String descricao, BigDecimal preco,
                            int quantidadeAtual, Boolean disponibilidade, BigDecimal precoCustomizado,
                            int quantidadeCustomizada, String descricaoCustomizada, Boolean disponibilidadeCustomizada,
                            String observacao) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeAtual = quantidadeAtual;
        this.disponibilidade = disponibilidade;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.descricaoCustomizada = descricaoCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
    }

}
