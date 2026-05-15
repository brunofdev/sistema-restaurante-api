package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.IdItemAvaliacaoVazioExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.ItemAvaliadoVazioExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_pedido_avaliado")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@AttributeOverrides({
        @AttributeOverride(name = "nota.valor", column = @Column(name = "item_nota_valor")),
        @AttributeOverride(name = "comentarioAvaliacao.valor", column = @Column(name = "item_comentario_valor"))
})
public class AvaliacaoItem extends Avaliavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "avaliacao_id")
    @Setter
    @JsonIgnore
    private Avaliacao avaliacao;
    private Long produtoId;
    private String nomeProdutoAvaliacao;

    public static AvaliacaoItem criar(Long produtoId, String nomeItemPedido, NotaAvaliacao notaAvaliacao, ComentarioAvaliacao comentarioAvaliacao) {
        AvaliacaoItem avaliacaoItem = new AvaliacaoItem();
        avaliacaoItem.setProdutoId(produtoId);
        avaliacaoItem.setNomeProdutoAvaliacao(nomeItemPedido);
        avaliacaoItem.vincularAvaliacao(notaAvaliacao, comentarioAvaliacao);
        return avaliacaoItem;
    }

    private void setProdutoId(Long produtoId) {
        if (produtoId == null) {
            throw new IdItemAvaliacaoVazioExcecao("O id do produto avaliado não pode ser vazio");
        }
        this.produtoId = produtoId;
    }

    private void setNomeProdutoAvaliacao(String nomeProdutoAvaliacao) {
        if (nomeProdutoAvaliacao == null || nomeProdutoAvaliacao.isEmpty()) {
            throw new ItemAvaliadoVazioExcecao("Nome do pedido não pode ser vazio");
        }
        this.nomeProdutoAvaliacao = nomeProdutoAvaliacao;
    }
}
