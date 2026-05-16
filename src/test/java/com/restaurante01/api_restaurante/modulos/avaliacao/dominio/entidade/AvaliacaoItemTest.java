package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.IdItemAvaliacaoVazioExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.ItemAvaliadoVazioExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.RespostaAvaliacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AvaliacaoItemTest {

    // ==========================================
    // CENÁRIOS DE CRIAÇÃO
    // ==========================================

    @Test
    @DisplayName("Dado dados válidos, Quando criar item de avaliação, Então deve instanciar corretamente")
    void deveCriarItemComSucesso() {
        AvaliacaoItem item = AvaliacaoItemBuilder.umItem().construir();

        assertThat(item.getProdutoId()).isEqualTo(1L);
        assertThat(item.getNomeProdutoAvaliacao()).isEqualTo("Hamburguer Clássico");
        assertThat(item.getNota()).isNull();
        assertThat(item.getComentarioAvaliacao()).isNull();
    }

    @Test
    @DisplayName("Dado produtoId nulo, Quando criar item, Então lança exceção")
    void naoDeveCriarItemSemProdutoId() {
        assertThatThrownBy(() -> AvaliacaoItemBuilder.umItem().semProdutoId().construir())
                .isInstanceOf(IdItemAvaliacaoVazioExcecao.class)
                .hasMessage("O id do produto avaliado não pode ser vazio");
    }

    @Test
    @DisplayName("Dado nome do pedido nulo, Quando criar item, Então lança exceção")
    void naoDeveCriarItemSemNome() {
        assertThatThrownBy(() -> AvaliacaoItemBuilder.umItem().comNome(null).construir())
                .isInstanceOf(ItemAvaliadoVazioExcecao.class)
                .hasMessage("Nome do pedido não pode ser vazio");
    }

    @Test
    @DisplayName("Dado nome do pedido vazio, Quando criar item, Então lança exceção")
    void naoDeveCriarItemComNomeVazio() {
        assertThatThrownBy(() -> AvaliacaoItemBuilder.umItem().comNome("").construir())
                .isInstanceOf(ItemAvaliadoVazioExcecao.class)
                .hasMessage("Nome do pedido não pode ser vazio");
    }

    // ==========================================
    // CENÁRIOS DE VINCULAÇÃO (URNA DO ITEM)
    // ==========================================

    @Test
    @DisplayName("Dado nota e comentário presentes, Quando vincular, Então salva ambos")
    void deveVincularComNotaEComentario() {
        AvaliacaoItem item = AvaliacaoItemBuilder.umItem().construir();
        NotaAvaliacao nota = new NotaAvaliacao(5);
        ComentarioAvaliacao comentario = new ComentarioAvaliacao("Muito crocante!");

        item.vincularAvaliacao(new RespostaAvaliacao(nota, comentario));

        assertThat(item.getNota()).isEqualTo(nota);
        assertThat(item.getComentarioAvaliacao()).isEqualTo(comentario);
    }

    @Test
    @DisplayName("Dado apenas nota, Quando vincular, Então salva nota e aplica comentário padrão")
    void deveVincularApenasComNota() {
        AvaliacaoItem item = AvaliacaoItemBuilder.umItem().construir();
        NotaAvaliacao nota = new NotaAvaliacao(4);

        item.vincularAvaliacao(new RespostaAvaliacao(nota, null));

        assertThat(item.getNota()).isEqualTo(nota);
        assertThat(item.getComentarioAvaliacao().valor()).isEqualTo("Avaliação feita sem comentário");
    }

    @Test
    @DisplayName("Dado voto em branco (nulos), Quando vincular, Então mantem nulos")
    void devePermitirVotoEmBrancoNoItem() {
        AvaliacaoItem item = AvaliacaoItemBuilder.umItem().construir();

        item.vincularAvaliacao(new RespostaAvaliacao(null, null));

        assertThat(item.getNota()).isNull();
        assertThat(item.getComentarioAvaliacao()).isNull();
    }

    @Test
    @DisplayName("Dado comentário sem nota, Quando vincular, Então lança exceção")
    void naoDeveAceitarComentarioSemNota() {
        AvaliacaoItem item = AvaliacaoItemBuilder.umItem().construir();
        ComentarioAvaliacao comentario = new ComentarioAvaliacao("Faltou sal");

        assertThatThrownBy(() -> item.vincularAvaliacao(new RespostaAvaliacao(null, comentario)))
                .isInstanceOf(AvaliacaoInvalidaExcecao.class)
                .hasMessage("Avaliação obrigatoriamente deve possuir uma Nota.");
    }
}