package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraValorDesconto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InformacoesCupomDTO(
       @NotNull Long idCupom,
       @NotBlank String codigoCupom,
       @NotNull BigDecimal descontoDoCupom,
       @NotNull RegraValorDesconto regraDoCupom,
       @NotBlank String CupomCriadoPor
) {
}
