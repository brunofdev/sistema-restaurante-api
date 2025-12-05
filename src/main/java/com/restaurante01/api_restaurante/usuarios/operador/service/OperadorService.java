package com.restaurante01.api_restaurante.usuarios.operador.service;

import com.restaurante01.api_restaurante.usuarios.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.usuarios.operador.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.entity.Operador;
import com.restaurante01.api_restaurante.usuarios.operador.mapper.OperadorMapper;
import com.restaurante01.api_restaurante.usuarios.operador.repository.OperadorRepository;
import com.restaurante01.api_restaurante.usuarios.operador.validator.OperadorValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorService {

    private final OperadorRepository repository;
    private final OperadorValidator validator;
    private final OperadorMapper mapper;

    public OperadorService(OperadorRepository repository, OperadorValidator validator, OperadorMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    public OperadorDTO cadastrarNovoOperador(CadastrarOperadorDTO dtoComSenhaEncoded) {
        validator.validarNovoOperador(dtoComSenhaEncoded, false);
        Operador operador = repository.save(mapper.mappearNovoOperador(dtoComSenhaEncoded));
        return mapper.mapearOperadorParaOperadorDTO(operador);
    }

    public Operador encontrarOperadorPorCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow(
                () -> new UserDontFoundException("Cliente não encontrado"));
    }

    public List<OperadorDTO> listarOperadores() {
        return mapper.mapearListaClienteParaClienteDTO(repository.findAll());
    }
}
