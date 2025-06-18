package com.restaurante01.api_restaurante.services;

import com.restaurante01.api_restaurante.dto.BuscarCepDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuscarCepService {

    private final RestTemplate restTemplate = new RestTemplate();

    public BuscarCepDTO consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, BuscarCepDTO.class);
    }
}
