package com.restaurante01.api_restaurante.modulos.cupom.dominio.builder;


import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CriarCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.PeriodoCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import java.math.BigDecimal;

public class CriarCupomDTOBuilder {

    private String codigoCupom = "PROMO10";
    private TipoDesconto tipoDesconto = TipoDesconto.PORCENTAGEM;
    private RegraRecorrencia recorrencia = RegraRecorrencia.QUINZE_DIAS;
    private Integer quantidade = 50;
    private BigDecimal valorParaDesconto = new BigDecimal("10.00");
    private BigDecimal valorTotalMinPedido = new BigDecimal("50.00");
    private BigDecimal valorTotalMaxPedido = new BigDecimal("200.00");
    private Boolean estaAtivo = true;
    private PeriodoCupomDTO periodo = new PeriodoCupomDTO(
            "14/04/2026", "12:00", "14/06/2026", "15:00"
    );

    public static CriarCupomDTOBuilder umCupom() {
        return new CriarCupomDTOBuilder();
    }

    public CriarCupomDTOBuilder comCodigo(String codigo) {
        this.codigoCupom = codigo;
        return this;
    }
    public CriarCupomDTOBuilder comTipoDeDesconton(TipoDesconto tp){
        this.tipoDesconto = tp;
        return this;
    }
    public CriarCupomDTOBuilder comRegraDeRecorrencia(RegraRecorrencia regRec){
        this.recorrencia = regRec;
        return this;
    }

    public CriarCupomDTOBuilder comQuantidade(Integer qtd) {
        this.quantidade = qtd;
        return this;
    }

    public CriarCupomDTOBuilder inativo() {
        this.estaAtivo = false;
        return this;
    }

    public CriarCupomDTOBuilder comValorDesconto(BigDecimal valor) {
        this.valorParaDesconto = valor;
        return this;
    }

    public CriarCupomDTOBuilder comPeriodo(PeriodoCupomDTO periodo) {
        this.periodo = periodo;
        return this;
    }

    public CriarCupomDTO build() {
        return new CriarCupomDTO(
                codigoCupom,
                tipoDesconto,
                recorrencia,
                quantidade,
                valorParaDesconto,
                valorTotalMinPedido,
                valorTotalMaxPedido,
                estaAtivo,
                periodo
        );
    }
}