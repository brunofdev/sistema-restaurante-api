package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.IdItemAvaliacaoVazioExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.IdPedidoAvaliacaoVazioExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
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
    @ManyToOne
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;
    private Long itemPedidoId;
    private Long produtoId;
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "item_nota_valor"))
    private NotaAvaliacao notaAvaliacao;
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "item_comentario_valor"))
    private ComentarioAvaliacao comentarioAvaliacao;

    public static AvaliacaoItem criar(Long itemPedidoId, Long produtoId, NotaAvaliacao notaAvaliacao, ComentarioAvaliacao comentarioAvaliacao) {
        AvaliacaoItem avaliacaoItem = new AvaliacaoItem();
        avaliacaoItem.setIdPedido(itemPedidoId);
        avaliacaoItem.setProdutoId(produtoId);
        avaliacaoItem.notaAvaliacao = notaAvaliacao;
        avaliacaoItem.comentarioAvaliacao = comentarioAvaliacao;
        return  avaliacaoItem;
    }

    private void setIdPedido(Long itemPedidoId) {
        if (itemPedidoId == null) {
            throw new IdPedidoAvaliacaoVazioExcecao("Item pedido nao pode ser nulo.");
        }
        this.itemPedidoId = itemPedidoId;
    }
    private void setProdutoId(Long produtoId) {
        if(produtoId == null) {
            throw new IdItemAvaliacaoVazioExcecao("O id do produto avaliado não pode ser vazui");
        }
        this.produtoId = produtoId;
    }
    protected void vincularAvaliacao(NotaAvaliacao nota, ComentarioAvaliacao comentarioAvaliacao){
        if(nota == null){
            throw new AvaliacaoInvalidaExcecao("Avaliação obrigatóriamente de possuir uma Nota.");
        }
        this.notaAvaliacao = nota;
        this.comentarioAvaliacao = (comentarioAvaliacao == null) ? new ComentarioAvaliacao("Avaliação feita sem comentário") :  comentarioAvaliacao;
    }
}
