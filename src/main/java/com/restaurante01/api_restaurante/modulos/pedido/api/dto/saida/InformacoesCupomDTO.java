package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InformacoesCupomDTO(
       @NotNull Long idCupom,
       @NotBlank String codigoCupom,
       @NotNull BigDecimal descontoDoCupom,
       @NotNull TipoDesconto regraDoCupom,
       @NotBlank RegraRecorrencia regraRecorrencia
) {
}
