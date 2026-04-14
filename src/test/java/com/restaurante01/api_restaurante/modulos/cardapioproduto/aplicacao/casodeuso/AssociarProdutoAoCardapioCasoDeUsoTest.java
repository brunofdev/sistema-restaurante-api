package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.builders.CardapioProdutoBuilder;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.BuscarCardapioPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.mapeador.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.validador.CardapioProdutoValidator;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoExistenteCardapioProdutoException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.StatusPedidoInvalidoException;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociarProdutoAoCardapioCasoDeUsoTest {

    @Mock
    private CardapioProdutoRepositorio cardapioProdutoRepositorio;

    @Mock
    private CardapioProdutoMapper cardapioProdutoMapper;

    @Mock
    private CardapioProdutoValidator cardapioProdutoValidator;

    @Mock
    private BuscarCardapioPorIdCasoDeUso buscarCardapioPorIdCasoDeUso;

    @Mock
    private ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    @InjectMocks
    private AssociarProdutoAoCardapioCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve associar produto ao cardápio com sucesso quando associação não existe")
    void deveRetornarDTO_quandoAssociacaoNaoExiste() {
        CardapioProdutoAssociacaoEntradaDTO dto = new CardapioProdutoAssociacaoEntradaDTO(
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
        CardapioProduto novaAssociacao = new CardapioProduto(
                1L,
                cardapio,
                produto,
                BigDecimal.valueOf(18.99),
                5,
                "Teste descricao customizada",
                true,
                "Observacao de teste"
        );
        CardapioProdutoAssociacaoRespostaDTO cardapioProdutoAssociacaoRespostaDTO = new CardapioProdutoAssociacaoRespostaDTO(
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
        when(obterProdutoPorIdCasoDeUso.retornarEntidade(dto.getIdProduto())).thenReturn(produto);
        when(buscarCardapioPorIdCasoDeUso.executar(dto.getIdCardapio())).thenReturn(cardapio);
        when(cardapioProdutoMapper.mapearCardapioProduto(produto, cardapio, dto)).thenReturn(novaAssociacao);
        when(cardapioProdutoMapper.mapearCardapioProdutoAssociacaoDTO(novaAssociacao)).thenReturn(cardapioProdutoAssociacaoRespostaDTO);


        CardapioProdutoAssociacaoRespostaDTO resultado = casoDeUso.executar(dto);

        assertThat(resultado).isEqualTo(cardapioProdutoAssociacaoRespostaDTO);


        verify(cardapioProdutoRepositorio).save(novaAssociacao);
        verify(cardapioProdutoValidator).validarCardapioProdutoAssociacaoEntradaDTO(dto);


    }

    @Test
    @DisplayName("Deve lançar exceção quando já existe associação entre produto e cardápio")
    void deveLancarExcecao_quandoAssociacaoExiste() {
        CardapioProdutoAssociacaoEntradaDTO dto = new CardapioProdutoAssociacaoEntradaDTO(
                1L,
                1L, BigDecimal.valueOf(20.8),
                10,
                "teste descricao teste",
                true,
                "teste de observação");
        when(cardapioProdutoRepositorio.existeAssociacao(dto.getIdCardapio(), dto.getIdProduto())).thenReturn(true);


        assertThatThrownBy(() -> casoDeUso.executar(dto))
                .isInstanceOf(AssociacaoExistenteCardapioProdutoException.class)
                .hasMessage("Associação já existe");

        verifyNoInteractions(obterProdutoPorIdCasoDeUso);
        verifyNoInteractions(buscarCardapioPorIdCasoDeUso);
        verify(cardapioProdutoRepositorio, never()).save(any());

    }
}


