package com.restaurante01.api_restaurante.cardapioproduto.entity;


//Esta classe faz um intermediario entre produto e cardapio, para poder ter variações de preços e quantidades.

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "CARDAPIO_PRODUTO", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cardapio_id", "produto_id"})
})
public class CardapioProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cardapio_id", nullable = false)
    private Cardapio cardapio;
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;


    @Column(name = "preco_customizado")
    private BigDecimal precoCustomizado;
    @Column(name = "qtd_customizada")
    private Integer quantidadeCustomizada;
    @Column(name = "desc_customizada")
    private String descricaoCustomizada;
    @Column(name = "disp_customizada")
    private Boolean disponibilidadeCustomizada;
    @Column(name = "obs")
    private String observacao;



}
