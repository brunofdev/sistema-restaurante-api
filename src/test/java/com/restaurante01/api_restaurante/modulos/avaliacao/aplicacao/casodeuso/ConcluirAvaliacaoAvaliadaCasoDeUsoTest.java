package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.AvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.ItemAvaliadoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.ResponderAvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.AvaliacaoBuilder;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.ClassificacaoAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.RespostaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcluirAvaliacaoAvaliadaCasoDeUsoTest {

    @Mock
    private AvaliacaoRepositorio repositorio;

    @Mock
    private AvaliacaoMapeador mapeador;

    @InjectMocks
    private ConcluirAvaliacaoAvaliadaCasoDeUso casoDeUso;

    @Captor
    private ArgumentCaptor<Avaliacao> avaliacaoCaptor;

    private static final Long CLIENTE_ID = 200L;

    private Avaliacao avaliacaoDisponivel() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        ReflectionTestUtils.setField(avaliacao, "status", StatusAvaliacao.DISPONIVEL);
        return avaliacao;
    }

    // ==========================================
    // CENÁRIOS DE SUCESSO
    // ==========================================

    @Test
    @DisplayName("Dado DTO completo com nota e itens, Quando executar, Então conclui e salva a avaliação")
    void deveConcluirAvaliacaoComSucesso() {
        Avaliacao avaliacao = avaliacaoDisponivel();
        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(5, "Excelente!");
        ItemAvaliadoDTO itemDTO = new ItemAvaliadoDTO(1L, 1L, avaliacaoDTO);
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, avaliacaoDTO, List.of(itemDTO));

        RespostaAvaliacao respostaMapeada = new RespostaAvaliacao(new NotaAvaliacao(5), new ComentarioAvaliacao("Excelente!"));

        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(avaliacao));
        when(mapeador.mapearRespostaAvaliacao(5, "Excelente!")).thenReturn(respostaMapeada);
        when(mapeador.mapearListaItensAvaliados(List.of(itemDTO))).thenReturn(Map.of());

        casoDeUso.executar(CLIENTE_ID, dto);

        verify(repositorio).salvar(avaliacaoCaptor.capture());
        assertThat(avaliacaoCaptor.getValue().getStatus()).isEqualTo(StatusAvaliacao.CONCLUIDA);
        assertThat(avaliacaoCaptor.getValue().getAvaliacao()).isEqualTo(ClassificacaoAvaliacao.SATISFEITO);
    }

    @Test
    @DisplayName("Dado DTO apenas com idAvaliacao (voto em branco), Quando executar, Então conclui como NAO_AVALIADO")
    void deveConcluirComVotoEmBranco() {
        Avaliacao avaliacao = avaliacaoDisponivel();
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, null, null);

        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(avaliacao));

        casoDeUso.executar(CLIENTE_ID, dto);

        verify(mapeador, never()).mapearRespostaAvaliacao(any(), any());
        verify(mapeador, never()).mapearListaItensAvaliados(any());
        verify(repositorio).salvar(avaliacaoCaptor.capture());
        assertThat(avaliacaoCaptor.getValue().getStatus()).isEqualTo(StatusAvaliacao.CONCLUIDA);
        assertThat(avaliacaoCaptor.getValue().getAvaliacao()).isEqualTo(ClassificacaoAvaliacao.NAO_AVALIADO);
    }

    // ==========================================
    // CENÁRIOS DE FALHA
    // ==========================================

    @Test
    @DisplayName("Dado um id inexistente, Quando executar, Então lança AvaliacaoInvalidaExcecao e não salva")
    void deveLancarExcecaoSeAvaliacaoNaoEncontrada() {
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(99L, null, null);
        when(repositorio.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> casoDeUso.executar(CLIENTE_ID, dto))
                .isInstanceOf(AvaliacaoInvalidaExcecao.class)
                .hasMessageContaining("99");

        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Dado que o cliente não é dono da avaliação, Quando executar, Então lança AvaliacaoInvalidaExcecao e não salva")
    void deveLancarExcecaoSeClienteNaoEhDono() {
        Long clienteIdErrado = 999L;
        Avaliacao avaliacao = avaliacaoDisponivel();
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, null, null);

        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(avaliacao));

        assertThatThrownBy(() -> casoDeUso.executar(clienteIdErrado, dto))
                .isInstanceOf(AvaliacaoInvalidaExcecao.class)
                .hasMessage("Cliente não é o dono desta avaliacao");

        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Dado avaliação com status PENDENTE, Quando executar, Então lança AvaliacaoInvalidaExcecao e não salva")
    void deveLancarExcecaoSeAvaliacaoNaoDisponivel() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir(); // status PENDENTE
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, null, null);

        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(avaliacao));

        assertThatThrownBy(() -> casoDeUso.executar(CLIENTE_ID, dto))
                .isInstanceOf(AvaliacaoInvalidaExcecao.class)
                .hasMessage("Só é possível concluir uma avaliação com status DISPONIVEL.");

        verify(repositorio, never()).salvar(any());
    }
}
