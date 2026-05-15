package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.porta;


import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.AvaliacaoParaNotificar;

//serviço de notificação deve implementar
public interface AvaliacaoNotificadorPorta {
    void notificarCliente(AvaliacaoParaNotificar avaliacao);
}
