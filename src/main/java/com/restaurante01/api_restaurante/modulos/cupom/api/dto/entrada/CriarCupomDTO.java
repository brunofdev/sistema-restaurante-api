package com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "Objeto de transferência de dados contendo as informações para a criação de um novo cupom.")
public record CriarCupomDTO(

        @Schema(description = "Código identificador do cupom que o cliente irá digitar.", example = "BEMVINDO20")
        @NotBlank(message = "O código do cupom é obrigatório")
        String codigoCupom,

        @Schema(description = "Tipo de desconto a ser aplicado (Ex: VALOR ou PORCENTAGEM).", example = "PORCENTAGEM")
        @NotNull(message = "O tipo de desconto é obrigatório")
        TipoDesconto tipoDesconto,

        @Schema(description = "Regra de recorrencia uso (Ex: SÓ PODE SER USADO DAQUI 15 OU 30 DIAS. VALORES ACEITOS: DEZ_DIAS | QUINZE_DIAS | VINTE_DIAS | TRINTA_DIAS).", example = "QUINZE_DIAS")
        @NotNull(message = "O tipo de desconto é obrigatório")
        RegraRecorrencia regraRecorrencia,

        @Schema(description = "Quantidade de vezes que o cupom pode ser utilizado por todos os clientes no total.", example = "50")
        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 0, message = "A quantidade não pode ser negativa")
        @Max(value = 100, message = "A quantidade máxima permitida é 100")
        Integer quantidade,

        @Schema(description = "Valor a ser descontado. Pode representar Reais (R$) ou uma Porcentagem (%), dependendo do tipo.", example = "15.00")
        @NotNull(message = "O valor para desconto é obrigatório")
        @Positive(message = "O valor para desconto deve ser maior que zero")
        BigDecimal valorParaDesconto,

        @Schema(description = "Valor mínimo que o pedido deve atingir para que o cupom seja aceito. A regra de negócio exige mínimo de R$ 20,00.", example = "50.00")
        @NotNull(message = "O valor mínimo do pedido é obrigatório")
        @DecimalMin(value = "20.0", message = "O valor mínimo do pedido não deve ser menor que 20")
        BigDecimal valorTotalMinPedido,

        @Schema(description = "Valor máximo do pedido para uso do cupom. A regra exige que seja maior que R$ 30,00.", example = "200.00")
        @NotNull(message = "O valor máximo do pedido é obrigatório")
        @DecimalMin(value = "30.0", inclusive = false, message = "O valor máximo do pedido deve ser maior que 30")
        BigDecimal valorTotalMaxPedido,

        @Schema(description = "Define se o cupom já estará disponível para uso imediatamente após a criação.", example = "true")
        @NotNull(message = "O status de ativação é obrigatório")
        Boolean estaAtivo,

        @Schema(description = "Dados do período de validade (Início e Fim).")
        @NotNull(message = "O período do cupom é obrigatório")
        PeriodoCupomDTO periodo
) {}