package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;


import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.ClassificacaoAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.TentativaNotificacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.*;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AvaliacaoTest {

    // ==========================================
    // CENÁRIOS DE CRIAÇÃO
    // ==========================================

    @Test
    @DisplayName("Dado um pedido, cliente e itens válidos, Quando criar avaliação, Então deve nascer PENDENTE e vincular os itens")
    void deveCriarAvaliacaoComSucesso() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();

        assertThat(avaliacao.getPedidoId()).isEqualTo(100L);
        assertThat(avaliacao.getClienteId()).isEqualTo(200L);
        assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.PENDENTE);
        assertThat(avaliacao.getDataCriacao()).isNotNull();
        assertThat(avaliacao.getDataExpiracao()).isAfter(avaliacao.getDataCriacao());


        assertThat(avaliacao.getItensAvaliados()).isNotEmpty();
        assertThat(avaliacao.getItensAvaliados().get(0).getAvaliacao()).isEqualTo(avaliacao);
    }

    @Test
    @DisplayName("Dado a falta de um ID de pedido, Quando tentar criar, Então deve falhar")
    void naoDeveCriarAvaliacaoSemPedido() {
        assertThatThrownBy(() -> AvaliacaoBuilder.umaAvaliacao().semPedido().construir())
                .isInstanceOf(IdPedidoAvaliacaoVazioExcecao.class);
    }

    @Test
    @DisplayName("Dado a falta de um ID de cliente, Quando tentar criar, Então deve falhar")
    void naoDeveCriarAvaliacaoSemCliente() {
        assertThatThrownBy(() -> AvaliacaoBuilder.umaAvaliacao().semCliente().construir())
                .isInstanceOf(IdClienteVazioExcecao.class);
    }

    @Test
    @DisplayName("Dado uma lista de itens vazia, Quando tentar criar, Então deve falhar")
    void naoDeveCriarAvaliacaoSemItens() {
        assertThatThrownBy(() -> AvaliacaoBuilder.umaAvaliacao().semItens().construir())
                .isInstanceOf(ItemAvaliadoVazioExcecao.class)
                .hasMessage("É obrigatório informar os produtos para serem avaliados");
    }

    // ==========================================
    // CENÁRIOS DE CONCLUSÃO (A URNA DE VOTOS)
    // ==========================================

    @Test
    @DisplayName("Dado nota e comentário presentes, Quando concluir avaliação, Então salva dados e deriva classificação")
    void deveConcluirComNotaEComentario() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        NotaAvaliacao nota = new NotaAvaliacao(5);
        ComentarioAvaliacao comentario = new ComentarioAvaliacao("Excelente!");

        ReflectionTestUtils.setField(avaliacao, "status", StatusAvaliacao.DISPONIVEL);

        avaliacao.concluirAvaliacao(nota, comentario);

        assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.CONCLUIDA);
        assertThat(avaliacao.getNota()).isEqualTo(nota);
        assertThat(avaliacao.getComentarioAvaliacao()).isEqualTo(comentario);
        assertThat(avaliacao.getAvaliacao()).isEqualTo(ClassificacaoAvaliacao.SATISFEITO);
    }

    @Test
    @DisplayName("Dado apenas uma nota sem comentário, Quando concluir avaliação, Então aplica comentário padrão")
    void deveConcluirApenasComNota() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        NotaAvaliacao nota = new NotaAvaliacao(3);
        ReflectionTestUtils.setField(avaliacao, "status", StatusAvaliacao.DISPONIVEL);

        avaliacao.concluirAvaliacao(nota, null);

        assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.CONCLUIDA);
        assertThat(avaliacao.getNota()).isEqualTo(nota);
        assertThat(avaliacao.getComentarioAvaliacao().valor()).isEqualTo("Avaliação feita sem comentário");
        assertThat(avaliacao.getAvaliacao()).isEqualTo(ClassificacaoAvaliacao.MODERADO);
    }

    @Test
    @DisplayName("Dado ausência de nota e comentário (voto em branco), Quando concluir avaliação, Então classifica como NAO_AVALIADO")
    void devePermitirVotoEmBranco() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        ReflectionTestUtils.setField(avaliacao, "status", StatusAvaliacao.DISPONIVEL);

        avaliacao.concluirAvaliacao(null, null);

        assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.CONCLUIDA);
        assertThat(avaliacao.getNota()).isNull();
        assertThat(avaliacao.getComentarioAvaliacao()).isNull();
        assertThat(avaliacao.getAvaliacao()).isEqualTo(ClassificacaoAvaliacao.NAO_AVALIADO);
    }

    @Test
    @DisplayName("Dado um comentário sem nota, Quando tentar concluir avaliação, Então lança exceção")
    void naoDeveAceitarComentarioSemNota() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        ComentarioAvaliacao comentario = new ComentarioAvaliacao("Chegou frio");
        ReflectionTestUtils.setField(avaliacao, "status", StatusAvaliacao.DISPONIVEL);

        assertThatThrownBy(() -> avaliacao.concluirAvaliacao(null, comentario))
                .isInstanceOf(AvaliacaoInvalidaExcecao.class)
                .hasMessage("Avaliação obrigatoriamente deve possuir uma Nota.");
    }

    // ==========================================
    // CENÁRIOS DE TRANSIÇÃO E EXPIRAÇÃO
    // ==========================================

    @Test
    @DisplayName("Dado uma avaliação já concluída, Quando tentar concluir novamente, Então lança erro de transição")
    void naoDeveConcluirAvaliacaoDuasVezes() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        ReflectionTestUtils.setField(avaliacao, "status", StatusAvaliacao.CONCLUIDA);

        assertThatThrownBy(() -> avaliacao.concluirAvaliacao(new NotaAvaliacao(5), null))
                .isInstanceOf(StatusAvaliacaoInvalidoExcecao.class);
    }

    @Test
    @DisplayName("Dado uma avaliação PENDENTE, Quando a data de expiração não chegou, Então falha ao tentar expirar")
    void naoDeveExpirarAntesDaData() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();

        assertThatThrownBy(avaliacao::expirarAvaliacao)
                .isInstanceOf(AvaliacaoNaoExpiradaExcecao.class);
    }

    @Test
    @DisplayName("Dado uma avaliação com data vencida, Quando pedir para expirar, Então altera status para EXPIRADA")
    void deveExpirarAvaliacaoVencida() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        ReflectionTestUtils.setField(avaliacao, "status", StatusAvaliacao.DISPONIVEL);
        ReflectionTestUtils.setField(avaliacao, "dataExpiracao", LocalDateTime.now().minusDays(1));

        avaliacao.expirarAvaliacao();

        assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.EXPIRADA);
    }

    // ==========================================
    // CENÁRIOS DE NOTIFICAÇÃO
    // ==========================================

    @Test
    @DisplayName("Dado uma avaliação nova, Quando notificada, Então define como PRIMEIRA_TENTATIVA")
    void deveRegistrarPrimeiraNotificacao() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();

        avaliacao.foiEnviadaAoCliente();

        assertThat(avaliacao.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.PRIMEIRA_TENTATIVA);
    }

    @Test
    @DisplayName("Dado uma avaliação com PRIMEIRA_TENTATIVA, Quando notificada novamente, Então avança para SEGUNDA_TENTATIVA")
    void deveAvancarParaSegundaNotificacao() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        avaliacao.foiEnviadaAoCliente();

        avaliacao.foiEnviadaAoCliente();

        assertThat(avaliacao.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.SEGUNDA_TENTATIVA);
    }

    @Test
    @DisplayName("Dado uma avaliação com SEGUNDA_TENTATIVA, Quando notificada novamente, Então avança para TERCEIRA_TENTATIVA")
    void deveAvancarParaTerceiraNotificacao() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        avaliacao.foiEnviadaAoCliente();
        avaliacao.foiEnviadaAoCliente();

        avaliacao.foiEnviadaAoCliente();

        assertThat(avaliacao.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.TERCEIRA_TENTATIVA);
    }
}