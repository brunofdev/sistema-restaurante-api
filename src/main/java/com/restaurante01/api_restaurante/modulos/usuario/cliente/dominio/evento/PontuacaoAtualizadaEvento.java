package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.evento;


public record PontuacaoAtualizadaEvento (
        Boolean pontuacaoFoiAtualizada,
        Long idPedido
){
}
