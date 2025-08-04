package com.restaurante01.api_restaurante.cep;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buscarcep")
public class BuscarCepController {
    private final BuscarCepService buscarCepService;

    public BuscarCepController(BuscarCepService buscarCepService){
        this.buscarCepService = buscarCepService;
    }

    @GetMapping("/{cep}")
    public BuscarCepDTO buscarEndereco(@PathVariable String cep) {
        return buscarCepService.consultarCep(cep);
    }
}
