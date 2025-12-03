package com.restaurante01.api_restaurante.usuarios.cliente.service;

import com.restaurante01.api_restaurante.usuarios.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public  ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }
}
