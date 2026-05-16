package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.RespostaAvaliacao;

public class AvaliacaoItemBuilder {
    private Long produtoId = 1L;
    private String nomeItemPedido = "Hamburguer Clássico";
    private NotaAvaliacao nota = null;
    private ComentarioAvaliacao comentario = null;

    public static AvaliacaoItemBuilder umItem() {
        return new AvaliacaoItemBuilder();
    }

    public AvaliacaoItemBuilder semProdutoId() {
        this.produtoId = null;
        return this;
    }

    public AvaliacaoItemBuilder comNome(String nome) {
        this.nomeItemPedido = nome;
        return this;
    }

    public AvaliacaoItemBuilder comNotaEComentarioInicial(Integer valorNota, String textoComentario) {
        this.nota = new NotaAvaliacao(valorNota);
        this.comentario = new ComentarioAvaliacao(textoComentario);
        return this;
    }

    public AvaliacaoItem construir() {
        return AvaliacaoItem.criar(this.produtoId, this.nomeItemPedido, new RespostaAvaliacao(this.nota, this.comentario));
    }
}