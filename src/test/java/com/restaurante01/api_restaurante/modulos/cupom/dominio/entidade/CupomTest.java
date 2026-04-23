package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CupomTest {

    private final LocalDate dataInicioValida = LocalDate.of(2026, 4, 14);
    private final LocalDate dataFimValida = LocalDate.of(2026, 6, 14);
    private final LocalTime horaInicioValida = LocalTime.of(12, 0);
    private final LocalTime horaFimValida = LocalTime.of(15, 0);

    private final LocalDate dataFimAntesDaInicio = LocalDate.of(2026, 1, 1);
    private final LocalTime horaFimAntesDaInicio = LocalTime.of(10, 0);

    private String codigoCupomValido;
    private PeriodoCupom periodoValido;
    private boolean cupomAtivo;
    private int quantidadeValida;
    private TipoDesconto regraPorcentagem;
    private BigDecimal valorDescontoValido;
    private BigDecimal valorMinimoPedidoValido;
    private BigDecimal valorMaximoPedidoValido;

    private String codigoCupomVazio = "";
    private int quantidadeInvalidaNegativa = -1;
    private BigDecimal descontoAcimaDoLimite = new BigDecimal("110.00");
    private BigDecimal descontoNegativo = new BigDecimal("-15.00");
    private BigDecimal valorMinimoInvalidoNegativo = new BigDecimal("-10.00");
    private BigDecimal limiteMinimoMaiorQueMaximo_Min = new BigDecimal("250.00");
    private BigDecimal limiteMinimoMaiorQueMaximo_Max = new BigDecimal("150.00");

    @BeforeEach
    void configurarCenario() {
        periodoValido = new PeriodoCupom("14/04/2026", "12:00", "14/06/2026", "15:00");
        codigoCupomValido = "CUPOM10";
        cupomAtivo = true;
        quantidadeValida = 10;
        regraPorcentagem = TipoDesconto.PORCENTAGEM;
        valorDescontoValido = new BigDecimal(15);
        valorMinimoPedidoValido = new BigDecimal(40);
        valorMaximoPedidoValido = new BigDecimal(150);
    }

    @Test
    @DisplayName("Deve criar um novo cupom valido")
    public void deveCriarCupomComDadosValidos() {
        Cupom cupom = Cupom.criar(
                codigoCupomValido,
                periodoValido,
                cupomAtivo,
                quantidadeValida,
                regraPorcentagem,
                valorDescontoValido,
                valorMinimoPedidoValido,
                valorMaximoPedidoValido
        );

        assertEquals("CUPOM10", cupom.getCodigoCupom().getValor());
        assertEquals(dataInicioValida, cupom.getPeriodoCupom().getDataInicio());
        assertEquals(horaInicioValida, cupom.getPeriodoCupom().getHoraInicio());
        assertEquals(dataFimValida, cupom.getPeriodoCupom().getDataFim());
        assertEquals(horaFimValida, cupom.getPeriodoCupom().getHoraFim());
        assertEquals(10, cupom.getQuantidade());
        assertEquals(TipoDesconto.PORCENTAGEM, cupom.getTipoDesconto());
        assertEquals(new BigDecimal(15), cupom.getValorParaDesconto());
        assertEquals(new BigDecimal(40), cupom.getValorTotalMinPedido());
        assertEquals(new BigDecimal(150), cupom.getValorTotalMaxPedido());
    }

    @Test
    @DisplayName("Deve lancar excecao ao criar cupom com quantidade negativa")
    public void deveLancarExcecaoQuantidadeNegativa() {
        assertThrows(QtdCupomInvalidaExcecao.class, () ->
                Cupom.criar(codigoCupomValido, periodoValido, cupomAtivo, quantidadeInvalidaNegativa, regraPorcentagem, valorDescontoValido, valorMinimoPedidoValido, valorMaximoPedidoValido)
        );
    }
    @Test
    @DisplayName("Deve lancar excecao ao criar cupom com valorDesconto = 0 ou negativo")
    public void deveLancarExcecaoValorDescontoNegativo() {
        assertThrows(ValorDescontoCupomExcecao.class, () ->
                Cupom.criar(codigoCupomValido, periodoValido, cupomAtivo, quantidadeValida, regraPorcentagem, descontoNegativo, valorMinimoPedidoValido, valorMaximoPedidoValido)
        );
    }
    @Test
    @DisplayName("Deve lancar excecao ao criar cupom com Regra de valor que é enum  = null")
    public void deveLancarExcecaoRegraValorCupomNull() {
        assertThrows(RegraCupomInvalidaExcecao.class, () ->
                Cupom.criar(codigoCupomValido, periodoValido, cupomAtivo, quantidadeValida, null, valorDescontoValido, valorMinimoPedidoValido, valorMaximoPedidoValido)
        );
    }
    @Test
    @DisplayName("Deve lancar excecao ao criar cupom valor minimo do pedido menor ou igual a zero")
    public void deveLancarExcecaoValorMinPedidoMenorIgualZero() {
        assertThrows(ValorMinPedidoExcecao.class, () ->
                Cupom.criar(codigoCupomValido, periodoValido, cupomAtivo, quantidadeValida, regraPorcentagem, valorDescontoValido, valorMinimoInvalidoNegativo, valorMaximoPedidoValido)
        );
    }
    @Test
    @DisplayName("Deve lancar excecao ao criar cupom valor minimo do pedido menor que 50")
    public void deveLancarExcecaoValorMinPedidoMenorQueQuarenta() {
        BigDecimal valorMinInvalido = new BigDecimal(10);
        assertThrows(ValorMinPedidoExcecao.class, () ->
                Cupom.criar(codigoCupomValido, periodoValido, cupomAtivo, quantidadeValida, regraPorcentagem, valorDescontoValido, valorMinInvalido, valorMaximoPedidoValido)
        );
    }
    @Test
    @DisplayName("Deve lancar excecao ao criar cupom valor maximo do pedido = zero")
    public void deveLancarExcecaoValorMaxZero() {
        BigDecimal valorMaxInvalido = new BigDecimal(0);
        assertThrows(ValorMaxPedidoExcecao.class, () ->
                Cupom.criar(codigoCupomValido, periodoValido, cupomAtivo, quantidadeValida, regraPorcentagem, valorDescontoValido, valorMinimoPedidoValido, valorMaxInvalido)
        );
    }
    @Test
    @DisplayName("Deve lancar excecao ao criar cupom com regra = PORCENTAGEM e valor Max maior que 300")
    public void deveLancarExcecaoValorMaxSeRegraPorcentagem() {
        BigDecimal valorMax = new BigDecimal(310);
        TipoDesconto regra = TipoDesconto.PORCENTAGEM;
        assertThrows(ValorMaxPedidoExcecao.class, () ->
                Cupom.criar(codigoCupomValido, periodoValido, cupomAtivo, quantidadeValida, regra, valorDescontoValido, valorMinimoPedidoValido, valorMax)
        );
    }
}