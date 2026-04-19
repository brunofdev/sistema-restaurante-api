package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.InvalidCredentialsException;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador.ClienteMapper;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticarClienteCasoDeUso {

    private final ObterClientePorCpfCasoDeUso obterClientePorCpf;
    private final PasswordEncoder passwordEncoder;
    private final ClienteMapper mapper;

    public AutenticarClienteCasoDeUso(ObterClientePorCpfCasoDeUso obterClientePorCpf, PasswordEncoder passwordEncoder, ClienteMapper mapper) {
        this.obterClientePorCpf = obterClientePorCpf;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public ClienteDTO executar(String cpf, String senha) {
        Cliente cliente = obterClientePorCpf.retornarEntidade(cpf);

        if (!passwordEncoder.matches(senha, cliente.getSenha())) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }

        return mapper.mapearClienteParaClienteDTO(cliente);
    }
}