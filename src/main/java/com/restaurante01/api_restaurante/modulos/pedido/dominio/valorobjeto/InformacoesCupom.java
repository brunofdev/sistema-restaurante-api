package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraValorDesconto;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Embeddable
public record InformacoesCupom (
    @NotNull Long idCupom,
    @NotBlank String codigoCupom,
    @NotNull BigDecimal descontoDoCupom,
    @NotNull RegraValorDesconto regraDoCupom,
    @NotBlank String CupomCriadoPor
){
}
