package com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Objeto de saída com detalhes completos do cupom. Exclusivo para visão administrativa.")
public record CupomAdminDTO(

        @Schema(description = "ID do cupom no banco de dados", example = "1")
        Long id,

        @Schema(description = "Código de texto utilizado pelo cliente", example = "BEMVINDO20")
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
        PeriodoCupomSaidaDTO periodo,


        @Schema(description = "Data e hora em que o cupom foi criado")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCriacao,

        @Schema(description = "Data e hora da última modificação")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataAtualizacao,

        @Schema(description = "Usuário (admin) que criou o cupom", example = "admin_joao")
        String criadoPor,

        @Schema(description = "Usuário (admin) que fez a última alteração", example = "admin_maria")
        String atualizadoPor
) {

}