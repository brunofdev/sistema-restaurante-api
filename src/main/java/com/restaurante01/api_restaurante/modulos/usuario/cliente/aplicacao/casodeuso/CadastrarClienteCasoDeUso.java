package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador.ClienteMapeador;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.validador.ClienteValidador;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarClienteCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteValidador validator;
    private final ClienteMapeador mapper;

    public CadastrarClienteCasoDeUso(ClienteRepositorio repository, ClienteValidador validator, ClienteMapeador mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Transactional
    public ClienteDTO executar(CadastrarClienteDTO dto) {
        validator.validarNovoCliente(dto, false);
        Cliente cliente = mapper.mappearNovoCliente(dto);
        Cliente clienteSalvo = repository.salvar(cliente);
        return mapper.mapearClienteParaClienteDTO(clienteSalvo);
    }
}