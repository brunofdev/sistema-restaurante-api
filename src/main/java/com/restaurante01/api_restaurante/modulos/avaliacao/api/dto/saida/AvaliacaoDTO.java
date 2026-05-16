package com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.ClassificacaoAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.TentativaNotificacao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Visão administrativa completa de uma avaliação de pedido.")
public record AvaliacaoDTO(

        @Schema(description = "ID da avaliação", example = "7")
        Long id,

        @Schema(description = "ID do pedido vinculado a esta avaliação", example = "42")
        Long pedidoId,

        @Schema(description = "ID do cliente que realizou o pedido", example = "15")
        Long clienteId,

        @Schema(description = "Status atual da avaliação no fluxo do sistema", example = "CONCLUIDA")
        StatusAvaliacao status,

        @Schema(description = "Classificação derivada da nota geral. Nula se o cliente não avaliou.", example = "SATISFEITO")
        ClassificacaoAvaliacao classificacao,

        @Schema(description = "Nota geral atribuída pelo cliente, de 1 a 5. Nula se não informada.", example = "5")
        Integer nota,

        @Schema(description = "Comentário geral do cliente sobre o pedido. Nulo se não informado.", example = "Entrega rápida e comida deliciosa!")
        String comentario,

        @Schema(description = "Indica quantas tentativas de notificação ao cliente já foram realizadas.", example = "PRIMEIRA_TENTATIVA")
        TentativaNotificacao numeroNotificacaoCliente,

        @Schema(description = "Data e hora em que a avaliação foi criada.")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCriacao,

        @Schema(description = "Data e hora limite para o cliente responder. Após esta data, a avaliação expira.")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataExpiracao,

        @Schema(description = "Lista de itens do pedido avaliados individualmente.")
        List<AvaliacaoItemDTO> itensAvaliados
) {
}
