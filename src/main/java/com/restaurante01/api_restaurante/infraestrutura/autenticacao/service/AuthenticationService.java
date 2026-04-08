package com.restaurante01.api_restaurante.infraestrutura.autenticacao.service;


import com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto.ClienteLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto.OperadorLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.service.ClienteService;
import com.restaurante01.api_restaurante.modulos.operador.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.service.OperadorService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtProvider jwtProvider;
    private final ClienteService clienteService;
    private final OperadorService operadorService;

    public AuthenticationService( JwtProvider jwtProvider, ClienteService clienteService, OperadorService operadorService){
        this.clienteService = clienteService;
        this.operadorService = operadorService;
        this.jwtProvider = jwtProvider;
    }

    public ClienteLoginResponseDTO loginCliente(CredenciaisDTO credentials) {
        ClienteDTO cliente = clienteService.autenticarCliente(credentials.cpf(), credentials.senha());
        String token = jwtProvider.generateToken(cliente.userName(), cliente.role());
        return new ClienteLoginResponseDTO(token, cliente);
    }
    public OperadorLoginResponseDTO loginOperador(CredenciaisDTO credentials) {
        OperadorDTO operadorDTO = operadorService.autenticarOperador(credentials.cpf(), credentials.senha());
        String token = jwtProvider.generateToken(operadorDTO.userName(), operadorDTO.role());
        return new OperadorLoginResponseDTO(token, operadorDTO);
    }
}
