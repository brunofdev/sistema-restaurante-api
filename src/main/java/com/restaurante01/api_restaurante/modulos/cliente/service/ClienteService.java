package com.restaurante01.api_restaurante.modulos.cliente.service;

import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.service.CalculadoraDeFidelidade;
import com.restaurante01.api_restaurante.modulos.cliente.validator.ClienteValidator;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entity.Cliente;
import com.restaurante01.api_restaurante.modulos.cliente.mapper.ClienteMapper;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repository.ClienteRepository;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.InvalidCredentialsException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;
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
    private final CalculadoraDeFidelidade calculadoraDeFidelidade;

    public  ClienteService(ClienteRepository repository, ClienteValidator validator, ClienteMapper mapper, PasswordEncoder passwordEncoder, CalculadoraDeFidelidade calculadoraDeFidelidade){
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.calculadoraDeFidelidade = calculadoraDeFidelidade;
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
        int pontuacao = calculadoraDeFidelidade.calcular(totalPedido);
        cliente.acrescentarPontuacao(pontuacao);
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
