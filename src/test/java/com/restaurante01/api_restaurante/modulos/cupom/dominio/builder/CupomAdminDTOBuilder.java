package com.restaurante01.api_restaurante.modulos.cupom.dominio.builder;

import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.PeriodoCupomSaidaDTO;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CupomAdminDTOBuilder {

    private Long id = 1L;
    private String codigo = "PROMO10";
    private TipoDesconto tipoDesconto = TipoDesconto.PORCENTAGEM;
    private Integer quantidadeRestante = 10;
    private BigDecimal valorDesconto = BigDecimal.valueOf(10);
    private BigDecimal valorTotalMinPedido = BigDecimal.valueOf(50);
    private BigDecimal valorTotalMaxPedido = BigDecimal.valueOf(200);
    private Boolean estaAtivo = true;
    private PeriodoCupomSaidaDTO periodo = new PeriodoCupomSaidaDTO(
            "01/01/2026", "08:00", "31/12/2026", "23:59");
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataAtualizacao = LocalDateTime.now();
    private String criadoPor = "admin_teste";
    private String atualizadoPor = "admin_teste";

    private CupomAdminDTOBuilder() {}

    public static CupomAdminDTOBuilder umCupomAdminDTO() {
        return new CupomAdminDTOBuilder();
    }

    public CupomAdminDTOBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public CupomAdminDTOBuilder comCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public CupomAdminDTOBuilder comTipoDesconto(TipoDesconto tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
        return this;
    }

    public CupomAdminDTOBuilder comQuantidadeRestante(Integer quantidadeRestante) {
        this.quantidadeRestante = quantidadeRestante;
        return this;
    }

    public CupomAdminDTOBuilder comValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
        return this;
    }

    public CupomAdminDTOBuilder comValorTotalMinPedido(BigDecimal valor) {
        this.valorTotalMinPedido = valor;
        return this;
    }

    public CupomAdminDTOBuilder comValorTotalMaxPedido(BigDecimal valor) {
        this.valorTotalMaxPedido = valor;
        return this;
    }

    public CupomAdminDTOBuilder comEstaAtivo(Boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
        return this;
    }

    public CupomAdminDTOBuilder comPeriodo(PeriodoCupomSaidaDTO periodo) {
        this.periodo = periodo;
        return this;
    }

    public CupomAdminDTOBuilder comDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public CupomAdminDTOBuilder comDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public CupomAdminDTOBuilder comCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
        return this;
    }

    public CupomAdminDTOBuilder comAtualizadoPor(String atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
        return this;
    }

    public CupomAdminDTO build() {
        return new CupomAdminDTO(
                id,
                codigo,
                tipoDesconto,
                quantidadeRestante,
                valorDesconto,
                valorTotalMinPedido,
                valorTotalMaxPedido,
                estaAtivo,
                periodo,
                dataCriacao,
                dataAtualizacao,
                criadoPor,
                atualizadoPor
        );
    }
}