package com.restaurante01.api_restaurante.modulos.operador.service;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.InvalidCredentialsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.modulos.operador.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.entity.Operador;
import com.restaurante01.api_restaurante.modulos.operador.mapper.OperadorMapper;
import com.restaurante01.api_restaurante.modulos.operador.repository.OperadorRepository;
import com.restaurante01.api_restaurante.modulos.operador.validator.OperadorValidator;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;
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
        Operador operador = mapper.mappearNovoOperador(dtoComSenhaEncoded);
        operador.setRole(Role.ADMIN1);
        repository.save(operador);
        return mapper.mapearOperadorParaOperadorDTO(operador);
    }

    public Operador encontrarOperadorPorCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow(
                () -> new UserDontFoundException("Cliente não encontrado"));
    }
    public boolean encontrarOperadorPorUserName(String userName){
        return repository.existsByUserName(userName);
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
