package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor;

//isso aqui precisa pensar ainda em como fazer, pois precisa de dados completos para enviar a notificação
public record AvaliacaoParaNotificar(Long avaliacaoId, Long clienteId, Long pedidoId) {
}
