package com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada;


import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Objeto de entrada para atualização de cupom")
public record CupomAtualizadoDTO(

        @Schema(description = "Código de texto utilizado pelo cliente", example = "JULHO10")
        String codigo,

        @Schema(description = "Tipo do desconto aplicado", example = "PORCENTAGEM")
        TipoDesconto tipoDesconto,

        @Schema(description = "Quantidade de usos ainda disponíveis", example = "45")
        Integer quantidadeRestante,

        @Schema(description = "Valor absoluto ou percentual do desconto", example = "15.00")
        BigDecimal valorDesconto,

        @Schema(description = "Valor mínimo de pedido exigido", example = "50.00")
        BigDecimal valorTotalMinPedido,

        @Schema(description = "Valor máximo de pedido permitido", example = "200.00")
        BigDecimal valorTotalMaxPedido,

        @Schema(description = "Status atual do cupom (se pode ou não ser usado)", example = "true")
        Boolean estaAtivo,

        @Schema(description = "Período de vigência do cupom")
        PeriodoCupomDTO periodo

) {

}