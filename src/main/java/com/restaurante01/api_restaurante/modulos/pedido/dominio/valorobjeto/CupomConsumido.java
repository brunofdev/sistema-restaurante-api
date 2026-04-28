package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Embeddable
public record CupomConsumido(
    @NotNull Long idCupom,
    @NotBlank String codigoCupom,
    @NotNull BigDecimal valorParaDesconto,
    @NotNull TipoDesconto regraDoCupom,
    @NotNull RegraRecorrencia regraRecorrencia
){
}
