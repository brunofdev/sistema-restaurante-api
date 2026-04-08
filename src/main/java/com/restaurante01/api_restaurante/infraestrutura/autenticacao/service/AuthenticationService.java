package com.restaurante01.api_restaurante.infraestrutura.autenticacao.service;

import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.ClienteLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.OperadorLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.aplicacao.casodeuso.AutenticarClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.aplicacao.casodeuso.AutenticarOperadorCasoDeUso;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtProvider jwtProvider;
    private final AutenticarClienteCasoDeUso autenticarCliente;
    private final AutenticarOperadorCasoDeUso autenticarOperador;

    public AuthenticationService(
            JwtProvider jwtProvider,
            AutenticarClienteCasoDeUso autenticarCliente,
            AutenticarOperadorCasoDeUso autenticarOperador) {
        this.jwtProvider = jwtProvider;
        this.autenticarCliente = autenticarCliente;
        this.autenticarOperador = autenticarOperador;
    }

    public ClienteLoginResponseDTO loginCliente(CredenciaisDTO credentials) {
        ClienteDTO cliente = autenticarCliente.executar(credentials.cpf(), credentials.senha());
        String token = jwtProvider.generateToken(cliente.userName(), cliente.role());
        return new ClienteLoginResponseDTO(token, cliente);
    }

    public OperadorLoginResponseDTO loginOperador(CredenciaisDTO credentials) {
        OperadorDTO operadorDTO = autenticarOperador.executar(credentials.cpf(), credentials.senha());
        String token = jwtProvider.generateToken(operadorDTO.userName(), operadorDTO.role());
        return new OperadorLoginResponseDTO(token, operadorDTO);
    }
}