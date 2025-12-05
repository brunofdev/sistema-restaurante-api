package com.restaurante01.api_restaurante.usuarios.cliente.service;

import com.restaurante01.api_restaurante.usuarios.cliente.validator.ClienteValidator;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.entity.Cliente;
import com.restaurante01.api_restaurante.usuarios.cliente.mapper.ClienteMapper;
import com.restaurante01.api_restaurante.usuarios.cliente.repository.ClienteRepository;
import com.restaurante01.api_restaurante.usuarios.exceptions.UserDontFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    private final ClienteValidator validator;
    private final ClienteMapper mapper;

    public  ClienteService(ClienteRepository repository, ClienteValidator validator, ClienteMapper mapper){
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    public ClienteDTO cadastrarNovoCliente(CadastrarClienteDTO dtoComSenhaEncoded) {
        validator.validarNovoCliente(dtoComSenhaEncoded, false);
        Cliente cliente = repository.save(mapper.mappearNovoCliente(dtoComSenhaEncoded));
        return mapper.mapearClienteParaClienteDTO(cliente);
    }
    public Cliente encontrarClientePorCpf(String cpf){
        return repository.findByCpf(cpf).orElseThrow(
                () -> new UserDontFoundException("Cliente não encontrado"));
    }
    public List<ClienteDTO> listarClientes() {
        return mapper.mapearListaClienteParaClienteDTO(repository.findAll());
    }
}
