package com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida;


import java.time.LocalDateTime;
import java.util.List;

//ESTE DTO DEVE APARECER APENAS PARA O USUARIO QUE É DONO DA AVALIAÇÃO, OU SEJA, QUE FEZ O PEDIDO
public record AvaliacaoPendenteClienteDTO(
        Long idDaAvaliacao,
        LocalDateTime dataEntregaDoPedido, // TODO: substituir por dataEntregaDoPedido real quando disponível
        List<ItensDoPedidoSaidaDTO> itensDoPedido
) {
}
