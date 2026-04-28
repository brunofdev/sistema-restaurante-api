package com.restaurante01.api_restaurante.modulos.cupom.dominio.builder;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.PeriodoCupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CupomBuilder {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Long id = 23L;
    private String codigo = "CUPOM10";
    private boolean estaAtivo = true;
    private int quantidade = 10;
    private TipoDesconto tipoDesconto = TipoDesconto.VALOR;
    private RegraRecorrencia recorrencia = RegraRecorrencia.QUINZE_DIAS;
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

    public CupomBuilder comId(Long id){
        this.id = id;
        return this;
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

    // ── Tipo de Desconto ──────────────────────────────────────────────────────

    public CupomBuilder baseadoEmPorcentagem(){
        this.tipoDesconto = TipoDesconto.PORCENTAGEM;
        return this;
    }

    public CupomBuilder baseadoEmValor(){
        this.tipoDesconto = TipoDesconto.VALOR;
        return this;
    }

    // ── Regras de Recorrência ─────────────────────────────────────────────────

    public CupomBuilder recorrenciaDe15Dias(){
        this.recorrencia = RegraRecorrencia.QUINZE_DIAS;
        return this;
    }

    public CupomBuilder recorrenciaDe30Dias(){
        this.recorrencia = RegraRecorrencia.TRINTA_DIAS;
        return this;
    }

    // ── Estoque / Quantidade ──────────────────────────────────────────────────

    public CupomBuilder comQuantidade(int quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public CupomBuilder semEstoque() {
        this.quantidade = 0;
        return this;
    }

    // ── Valores e Limites ─────────────────────────────────────────────────────

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

    // ── Período de Vigência ───────────────────────────────────────────────────

    public CupomBuilder vigente() {
        this.dataInicio = LocalDate.now().minusDays(1).format(FMT);
        this.horaInicio = "00:00";
        this.dataFim    = LocalDate.now().plusDays(30).format(FMT);
        this.horaFim    = "23:59";
        return this;
    }

    public CupomBuilder expirado() {
        this.dataInicio = LocalDate.now().minusDays(60).format(FMT);
        this.horaInicio = "00:00";
        this.dataFim    = LocalDate.now().minusDays(1).format(FMT);
        this.horaFim    = "23:59";
        return this;
    }

    public CupomBuilder aindaNaoVigente() {
        this.dataInicio = LocalDate.now().plusDays(5).format(FMT);
        this.horaInicio = "00:00";
        this.dataFim    = LocalDate.now().plusDays(35).format(FMT);
        this.horaFim    = "23:59";
        return this;
    }

    public CupomBuilder comPeriodoCustomizado(String dataInicio, String horaInicio,
                                              String dataFim,    String horaFim) {
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.dataFim    = dataFim;
        this.horaFim    = horaFim;
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
                recorrencia,
                valorParaDesconto,
                valorTotalMinPedido,
                valorTotalMaxPedido
        );
    }
}