package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.ItemAvaliadoVazioExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.IdItemAvaliacaoVazioExcecao;
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
    @Setter
    @JsonIgnore //quando tiver dtos da pra remover
    private Avaliacao avaliacao;
    private Long produtoId;
    private String nomeProdutoAvaliacao;
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "item_nota_valor"))
    private NotaAvaliacao nota;
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "item_comentario_valor"))
    private ComentarioAvaliacao comentarioAvaliacao;

    public static AvaliacaoItem criar(Long produtoId,String nomeItemPedido, NotaAvaliacao notaAvaliacao, ComentarioAvaliacao comentarioAvaliacao) {
        AvaliacaoItem avaliacaoItem = new AvaliacaoItem();
        avaliacaoItem.setProdutoId(produtoId);
        avaliacaoItem.setNomeProdutoAvaliacao(nomeItemPedido);
        avaliacaoItem.nota = notaAvaliacao;
        avaliacaoItem.comentarioAvaliacao = comentarioAvaliacao;
        return  avaliacaoItem;
    }

    private void setProdutoId(Long produtoId) {
        if(produtoId == null) {
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

    protected void vincularAvaliacao(NotaAvaliacao nota, ComentarioAvaliacao comentarioAvaliacao){
        if(nota == null && comentarioAvaliacao != null){
            throw new AvaliacaoInvalidaExcecao("Avaliação obrigatoriamente deve possuir uma Nota.");
        }
        //Voto em branco: aceita e não faz nada
        if(nota == null){
            this.nota = null;
            this.comentarioAvaliacao = null;
            return;
        }
        //Cenário de sucesso: preenche o que veio (usando ternário para o default)
        this.nota = nota;
        this.comentarioAvaliacao = (comentarioAvaliacao != null) ? comentarioAvaliacao : new ComentarioAvaliacao("Avaliação feita sem comentário");
    }
}
