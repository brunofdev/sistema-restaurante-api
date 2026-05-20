package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.schuduler;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizaPedidosPorStatusHandler {

    private final PedidoRepositorio repositorio;

    @Scheduled(fixedDelay = 30000)
    public void executar

}
