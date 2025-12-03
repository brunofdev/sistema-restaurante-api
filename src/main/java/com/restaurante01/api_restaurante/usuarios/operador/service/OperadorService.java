package com.restaurante01.api_restaurante.usuarios.operador.service;

import com.restaurante01.api_restaurante.usuarios.operador.repository.OperadorRepository;
import org.springframework.stereotype.Service;

@Service
public class OperadorService {

    private final OperadorRepository operadorRepository;

    public OperadorService(OperadorRepository operadorRepository){
        this.operadorRepository = operadorRepository;
    }
}
