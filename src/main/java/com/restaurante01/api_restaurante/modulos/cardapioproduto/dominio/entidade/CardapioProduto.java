package com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade;


//Esta classe faz um intermediario entre produto e cardapio, para poder ter variações de preços e quantidades.

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
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

    public void diminuirQuantidade(int quantidadeParaDiminuir){
        if (this.quantidadeCustomizada != null) {
            this.quantidadeCustomizada -= quantidadeParaDiminuir;
            if (this.quantidadeCustomizada < 0) {
                this.quantidadeCustomizada = 0;
            }
        }
    }
    public boolean verificaDisponibilidadeProduto(Integer quantidadeSolicitada){
        // 1. A Trava Mestre (Visibilidade)
        if (Boolean.FALSE.equals(this.disponibilidadeCustomizada)) {
            return false;
        }
        // 2. Regra do Estoque Customizado
        // ele só ignora se for NULO. Se for ZERO, ele entra aqui e recusa a venda (0 >= 1 é falso)
        if (this.quantidadeCustomizada != null) {
            return this.quantidadeCustomizada >= quantidadeSolicitada;
        }

        // 3. Fallback (Se a customizada não foi configurada [NULA], usa o estoque original)
        if (this.produto.getQuantidadeAtual() != null) {
            return this.produto.getQuantidadeAtual() >= quantidadeSolicitada;
        }

        return false;
    }


}
