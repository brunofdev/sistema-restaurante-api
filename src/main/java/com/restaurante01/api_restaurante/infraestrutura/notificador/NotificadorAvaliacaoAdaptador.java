package com.restaurante01.api_restaurante.infraestrutura.notificador;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.porta.AvaliacaoNotificadorPorta;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.AvaliacaoParaNotificar;
import org.springframework.stereotype.Component;


//deve chamar serviços de notificação
@Component
public class NotificadorAvaliacaoAdaptador implements AvaliacaoNotificadorPorta {

    @Override
    public void notificarCliente(AvaliacaoParaNotificar avaliacao){
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("Oba, o cliente foi notificado, o pedido dele é: " + avaliacao.pedidoId());
    }
}
