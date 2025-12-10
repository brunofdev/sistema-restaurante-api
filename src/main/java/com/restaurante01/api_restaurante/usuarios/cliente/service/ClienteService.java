package com.restaurante01.api_restaurante.usuarios.cliente.service;

import com.restaurante01.api_restaurante.usuarios.cliente.validator.ClienteValidator;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.entity.Cliente;
import com.restaurante01.api_restaurante.usuarios.cliente.mapper.ClienteMapper;
import com.restaurante01.api_restaurante.usuarios.cliente.repository.ClienteRepository;
import com.restaurante01.api_restaurante.usuarios.exceptions.InvalidCredentialsException;
import com.restaurante01.api_restaurante.usuarios.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.usuarios.role.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    private final ClienteValidator validator;
    private final ClienteMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public  ClienteService(ClienteRepository repository, ClienteValidator validator, ClienteMapper mapper, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    public ClienteDTO cadastrarNovoCliente(CadastrarClienteDTO dtoComSenhaEncoded) {
        validator.validarNovoCliente(dtoComSenhaEncoded, false);
        Cliente cliente = mapper.mappearNovoCliente(dtoComSenhaEncoded);
        cliente.setRole(Role.USER);
        repository.save(cliente);
        return mapper.mapearClienteParaClienteDTO(cliente);
    }
    public Cliente encontrarClientePorCpf(String cpf){
        return repository.findByCpf(cpf).orElseThrow(
                () -> new UserDontFoundException("Cliente não encontrado"));
    }
    public void atualizaPontuacaoFidelidadeCliente(Cliente cliente, BigDecimal totalPedido){
        cliente.acrescentarPontuacao(totalPedido);
        repository.save(cliente);
    }
    public boolean encontrarClientePorUserName(String userName){
        return repository.existsByUserName(userName);
    }
    public List<ClienteDTO> listarClientes() {
        return mapper.mapearListaClienteParaClienteDTO(repository.findAll());
    }
    public ClienteDTO autenticarCliente(String cpf, String senha){
        Cliente cliente = encontrarClientePorCpf(cpf);
        if (!passwordEncoder.matches(senha, cliente.getSenha())){
            throw new InvalidCredentialsException("Credenciais invalidas");
        };
        return mapper.mapearClienteParaClienteDTO(cliente);
    }
}
