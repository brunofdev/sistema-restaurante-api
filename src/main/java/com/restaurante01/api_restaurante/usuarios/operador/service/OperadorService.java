package com.restaurante01.api_restaurante.usuarios.operador.service;

import com.restaurante01.api_restaurante.usuarios.cliente.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.entity.Cliente;
import com.restaurante01.api_restaurante.usuarios.exceptions.InvalidCredentialsException;
import com.restaurante01.api_restaurante.usuarios.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.usuarios.operador.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.entity.Operador;
import com.restaurante01.api_restaurante.usuarios.operador.mapper.OperadorMapper;
import com.restaurante01.api_restaurante.usuarios.operador.repository.OperadorRepository;
import com.restaurante01.api_restaurante.usuarios.operador.validator.OperadorValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorService {

    private final OperadorRepository repository;
    private final OperadorValidator validator;
    private final OperadorMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public OperadorService(OperadorRepository repository, OperadorValidator validator, OperadorMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
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
    public OperadorDTO autenticarOperador(String cpf, String senha){
        Operador operador = encontrarOperadorPorCpf(cpf);
        if (!passwordEncoder.matches(senha, operador.getSenha())){
            throw new InvalidCredentialsException("Credenciais invalidas");
        };
        return mapper.mapearOperadorParaOperadorDTO(operador);
    }
}
