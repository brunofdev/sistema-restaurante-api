package com.restaurante01.api_restaurante.modulos.avaliacao.dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_pedido_avaliado")
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class AvaliacaoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long itemPedidoId;
    private Long produtoId;
    @Setter
    @Embedded
    private NotaAvaliacao notaAvaliacao;
    @Setter
    @Embedded
    private ComentarioAvaliacao comentarioAvaliacao;

    public static AvaliacaoItem criar(Long itemPedidoId, Long produtoId, NotaAvaliacao notaAvaliacao, ComentarioAvaliacao comentarioAvaliacao) {
        AvaliacaoItem avaliacaoItem = new AvaliacaoItem();
        avaliacaoItem.setIdPedido(itemPedidoId);
        avaliacaoItem.setProdutoId(produtoId);
        avaliacaoItem.setNotaAvaliacao(notaAvaliacao);
        avaliacaoItem.setComentarioAvaliacao(comentarioAvaliacao);
        return  avaliacaoItem;
    }

    public void setIdPedido(Long itemPedidoId) {
        if (itemPedidoId == null) {
            throw new IdPedidoAvaliacaoVazioExcecao("Item pedido nao pode ser nulo.");
        }
        this.itemPedidoId = itemPedidoId;
    }
    public void setProdutoId(Long produtoId) {
        if(produtoId == null) {
            throw new IdItemAvaliacaoVazioExcecao("O id do produto avaliado não pode ser vazui");
        }
        this.produtoId = produtoId;
    }



}
