package com.restaurante01.api_restaurante.cep;

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
