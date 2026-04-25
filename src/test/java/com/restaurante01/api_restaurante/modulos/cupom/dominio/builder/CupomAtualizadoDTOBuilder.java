package com.restaurante01.api_restaurante.modulos.cupom.dominio.builder;


import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CupomAtualizadoDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.PeriodoCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;

import java.math.BigDecimal;

public class CupomAtualizadoDTOBuilder {

    private String codigo = "JULHO10";
    private TipoDesconto tipoDesconto = TipoDesconto.PORCENTAGEM;
    private Integer quantidadeRestante = 45;
    private BigDecimal valorDesconto = BigDecimal.valueOf(15.00);
    private BigDecimal valorTotalMinPedido = BigDecimal.valueOf(50.00);
    private BigDecimal valorTotalMaxPedido = BigDecimal.valueOf(200.00);
    private Boolean estaAtivo = true;
    private PeriodoCupomDTO periodo = new PeriodoCupomDTO("01/01/2026", "08:00", "31/12/2026", "23:59");

    private CupomAtualizadoDTOBuilder() {}

    public static CupomAtualizadoDTOBuilder umCupomAtualizado() {
        return new CupomAtualizadoDTOBuilder();
    }

    public CupomAtualizadoDTOBuilder comCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public CupomAtualizadoDTOBuilder comTipoDesconto(TipoDesconto tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
        return this;
    }

    public CupomAtualizadoDTOBuilder comQuantidade(Integer quantidade) {
        this.quantidadeRestante = quantidade;
        return this;
    }

    public CupomAtualizadoDTOBuilder comValorDesconto(BigDecimal valor) {
        this.valorDesconto = valor;
        return this;
    }

    public CupomAtualizadoDTOBuilder comValorMinPedido(BigDecimal valor) {
        this.valorTotalMinPedido = valor;
        return this;
    }

    public CupomAtualizadoDTOBuilder comValorMaxPedido(BigDecimal valor) {
        this.valorTotalMaxPedido = valor;
        return this;
    }

    public CupomAtualizadoDTOBuilder comEstaAtivo(Boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
        return this;
    }

    public CupomAtualizadoDTOBuilder comPeriodo(PeriodoCupomDTO periodo) {
        this.periodo = periodo;
        return this;
    }

    public CupomAtualizadoDTO build() {
        return new CupomAtualizadoDTO(
                codigo,
                tipoDesconto,
                quantidadeRestante,
                valorDesconto,
                valorTotalMinPedido,
                valorTotalMaxPedido,
                estaAtivo,
                periodo
        );
    }
}