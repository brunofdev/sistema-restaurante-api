package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.BuscarCardapioPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.AssociarProdutoAoCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacaoFeitaRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.associacao.mapeador.CardapioProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador.associacao.validador.CardapioProdutoValidador;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.AssociacaoExistenteCardapioProdutoExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.porta.associacao.CardapioPorta;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.porta.associacao.ProdutoPorta;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.ObterProdutoPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociarProdutoAoCardapioCasoDeUsoTest {

    @Mock
    private CardapioProdutoRepositorio cardapioProdutoRepositorio;

    @Mock
    private CardapioProdutoMapeador cardapioProdutoMapeador;

    @Mock
    private CardapioProdutoValidador cardapioProdutoValidador;

    @Mock
    private CardapioPorta buscarCardapioPorIdCasoDeUso;

    @Mock
    private ProdutoPorta obterProdutoPorIdCasoDeUso;

    @InjectMocks
    private AssociarProdutoAoCardapioCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve associar produto ao cardápio com sucesso quando associação não existe")
    void deveRetornarDTO_quandoAssociacaoNaoExiste() {
        AssociacaoEntradaDTO dto = new AssociacaoEntradaDTO(
                1L,
                1L, BigDecimal.valueOf(20.8),
                10,
                "teste descricao teste",
                true,
                "teste de observação");
        Produto produto = new Produto(
                1L,
                "X-SALADA",
                "XIS COMPLETA SALADA COM MAIONESE",
                BigDecimal.valueOf(28.80),
                10L,
                true
        );
        Cardapio cardapio = new Cardapio(
                1L,
                "Cardapio de verão",
                "promoções de verão muito divertidas",
                true,
                LocalDate.now(),
                LocalDate.of(2026, 12, 28)
        );
        Associacao novaAssociacao = new Associacao(
                1L,
                cardapio,
                produto,
                BigDecimal.valueOf(18.99),
                5,
                "Teste descricao customizada",
                true,
                "Observacao de teste"
        );
        AssociacaoFeitaRespostaDTO associacaoFeitaRespostaDTO = new AssociacaoFeitaRespostaDTO(
                "Associação realizada",
                new CardapioDTO(1L, "Cardapioo de verão", "Promoção de verão muito divertidas", true, LocalDate.now(), LocalDate.of(2026, 12, 28)),
                new ProdutoDTO(1L, "X-SALADA", "XIS COMPLETA SALADA COM MAIONESE", BigDecimal.valueOf(28.80), 10L, true, LocalDateTime.now(), LocalDateTime.now(), "BRUNO", "BRUNO"),
                BigDecimal.valueOf(18.99),
                10,
                true,
                "Nenhuma observação",
                "Nenhuma Descrição Customizada"
        );

        when(cardapioProdutoRepositorio.existeAssociacao(dto.getIdCardapio(), dto.getIdProduto())).thenReturn(false);
        when(obterProdutoPorIdCasoDeUso.obterProdutoPorId(dto.getIdProduto())).thenReturn(produto);
        when(buscarCardapioPorIdCasoDeUso.buscarCardapioPorId(dto.getIdCardapio())).thenReturn(cardapio);
        when(cardapioProdutoMapeador.mapearCardapioProduto(produto, cardapio, dto)).thenReturn(novaAssociacao);
        when(cardapioProdutoMapeador.mapearCardapioProdutoAssociacaoDTO(novaAssociacao)).thenReturn(associacaoFeitaRespostaDTO);


        AssociacaoFeitaRespostaDTO resultado = casoDeUso.executar(dto);

        assertThat(resultado).isEqualTo(associacaoFeitaRespostaDTO);


        verify(cardapioProdutoRepositorio).save(novaAssociacao);
        verify(cardapioProdutoValidador).validarCardapioProdutoAssociacaoEntradaDTO(dto);


    }

    @Test
    @DisplayName("Deve lançar exceção quando já existe associação entre produto e cardápio")
    void deveLancarExcecao_quandoAssociacaoExiste() {
        AssociacaoEntradaDTO dto = new AssociacaoEntradaDTO(
                1L,
                1L, BigDecimal.valueOf(20.8),
                10,
                "teste descricao teste",
                true,
                "teste de observação");
        when(cardapioProdutoRepositorio.existeAssociacao(dto.getIdCardapio(), dto.getIdProduto())).thenReturn(true);


        assertThatThrownBy(() -> casoDeUso.executar(dto))
                .isInstanceOf(AssociacaoExistenteCardapioProdutoExcecao.class)
                .hasMessage("Associação já existe");

        verifyNoInteractions(obterProdutoPorIdCasoDeUso);
        verifyNoInteractions(buscarCardapioPorIdCasoDeUso);
        verify(cardapioProdutoRepositorio, never()).save(any());

    }
}


