package com.restaurante01.api_restaurante.autenticacao.service;


import com.restaurante01.api_restaurante.autenticacao.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.autenticacao.dto.LoginResponseDTO;
import com.restaurante01.api_restaurante.autenticacao.jwt.JwtProvider;
import com.restaurante01.api_restaurante.usuarios.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtProvider jwtProvider;
    private final UsuarioService usuarioService;

    public AuthenticationService( JwtProvider jwtProvider, UsuarioService usuarioService){
        this.usuarioService = usuarioService;
        this.jwtProvider = jwtProvider;
    }

    public LoginResponseDTO login(CredenciaisDTO credentials) {
        UsuarioDTO userData = usuarioService.autenticarUsuario(credentials.cpf(), credentials.senha());
        String token = jwtProvider.generateToken(userData.userName(), userData.role());
        return new LoginResponseDTO(token, userData);
    }
}
