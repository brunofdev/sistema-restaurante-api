package com.restaurante01.api_restaurante.infraestrutura.autenticacao.service;

import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.ClienteLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.OperadorLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.AutenticarClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso.AutenticarOperadorCasoDeUso;
import org.springframework.stereotype.Service;

@Service
public class ServicoAutenticacao {

    private final JwtProvider jwtProvider;
    private final AutenticarClienteCasoDeUso autenticarCliente;
    private final AutenticarOperadorCasoDeUso autenticarOperador;

    public ServicoAutenticacao(JwtProvider jwtProvider,
                               AutenticarClienteCasoDeUso autenticarCliente,
                               AutenticarOperadorCasoDeUso autenticarOperador) {
        this.jwtProvider = jwtProvider;
        this.autenticarCliente = autenticarCliente;
        this.autenticarOperador = autenticarOperador;
    }

    public ClienteLoginResponseDTO loginCliente(CredenciaisDTO credenciais) {
        ClienteDTO cliente = autenticarCliente.executar(credenciais.cpf(), credenciais.senha());
        String token = jwtProvider.gerarToken(cliente.cpf(), cliente.role());
        return new ClienteLoginResponseDTO(token, cliente);
    }

    public OperadorLoginResponseDTO loginOperador(CredenciaisDTO credenciais) {
        OperadorDTO operador = autenticarOperador.executar(credenciais.cpf(), credenciais.senha());
        String token = jwtProvider.gerarToken(operador.cpf(), operador.role());
        return new OperadorLoginResponseDTO(token, operador);
    }
}