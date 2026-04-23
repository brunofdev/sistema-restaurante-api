package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CupomBuilder {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String codigo = "CUPOM10";
    private boolean estaAtivo = true;
    private int quantidade = 10;
    private TipoDesconto tipoDesconto = TipoDesconto.VALOR;
    private BigDecimal valorParaDesconto = BigDecimal.valueOf(10);
    private BigDecimal valorTotalMinPedido = BigDecimal.valueOf(50);
    private BigDecimal valorTotalMaxPedido = BigDecimal.valueOf(200);

    // Período padrão: ontem às 00:00 até daqui 30 dias às 23:59
    private String dataInicio = LocalDate.now().minusDays(1).format(FMT);
    private String horaInicio = "00:00";
    private String dataFim    = LocalDate.now().plusDays(30).format(FMT);
    private String horaFim    = "23:59";

    public static CupomBuilder umCupom() {
        return new CupomBuilder();
    }

    // ── Código ────────────────────────────────────────────────────────────────

    public CupomBuilder comCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    // ── Status ────────────────────────────────────────────────────────────────

    public CupomBuilder ativo() {
        this.estaAtivo = true;
        return this;
    }

    public CupomBuilder inativo() {
        this.estaAtivo = false;
        return this;
    }

    // ── Quantidade ────────────────────────────────────────────────────────────

    public CupomBuilder comQuantidade(int quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public CupomBuilder semEstoque() {
        this.quantidade = 0;
        return this;
    }

    // ── Tipo de desconto ──────────────────────────────────────────────────────

    public CupomBuilder comTipoDesconto(TipoDesconto tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
        return this;
    }

    public CupomBuilder descontoPorcentagem() {
        this.tipoDesconto = TipoDesconto.PORCENTAGEM;
        return this;
    }

    public CupomBuilder descontoValor() {
        this.tipoDesconto = TipoDesconto.VALOR;
        return this;
    }

    // ── Valores ───────────────────────────────────────────────────────────────

    public CupomBuilder comValorDesconto(BigDecimal valor) {
        this.valorParaDesconto = valor;
        return this;
    }

    public CupomBuilder comValorMinPedido(BigDecimal valor) {
        this.valorTotalMinPedido = valor;
        return this;
    }

    public CupomBuilder comValorMaxPedido(BigDecimal valor) {
        this.valorTotalMaxPedido = valor;
        return this;
    }

    // ── Período ───────────────────────────────────────────────────────────────

    public CupomBuilder comPeriodo(String dataInicio, String horaInicio,
                                   String dataFim,    String horaFim) {
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.dataFim    = dataFim;
        this.horaFim    = horaFim;
        return this;
    }

    /** Período já encerrado ontem às 23:59 */
    public CupomBuilder expirado() {
        this.dataInicio = LocalDate.now().minusDays(60).format(FMT);
        this.horaInicio = "00:00";
        this.dataFim    = LocalDate.now().minusDays(1).format(FMT);
        this.horaFim    = "23:59";
        return this;
    }

    /** Período que começa daqui 5 dias (ainda não vigente) */
    public CupomBuilder aindaNaoVigente() {
        this.dataInicio = LocalDate.now().plusDays(5).format(FMT);
        this.horaInicio = "00:00";
        this.dataFim    = LocalDate.now().plusDays(35).format(FMT);
        this.horaFim    = "23:59";
        return this;
    }

    /** Período ativo agora (default, mas explícito quando o nome importa) */
    public CupomBuilder vigente() {
        this.dataInicio = LocalDate.now().minusDays(1).format(FMT);
        this.horaInicio = "00:00";
        this.dataFim    = LocalDate.now().plusDays(30).format(FMT);
        this.horaFim    = "23:59";
        return this;
    }

    // ── Build ─────────────────────────────────────────────────────────────────

    public Cupom build() {
        PeriodoCupom periodo = new PeriodoCupom(dataInicio, horaInicio, dataFim, horaFim);
        return Cupom.criar(
                codigo,
                periodo,
                estaAtivo,
                quantidade,
                tipoDesconto,
                valorParaDesconto,
                valorTotalMinPedido,
                valorTotalMaxPedido
        );
    }
}