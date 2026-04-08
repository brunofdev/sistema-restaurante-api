package com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.controller;

import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.ClienteLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.OperadorLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.AuthenticationService;
import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints públicos para login e geração de tokens JWT")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Login de Cliente", description = "Autentica um cliente via CPF e Senha, retornando os dados do perfil e o token JWT de acesso. Rota pública.")
    @PostMapping("/cliente-login")
    public ResponseEntity<ApiResponse<ClienteLoginResponseDTO>> loginCliente(@Valid @RequestBody CredenciaisDTO credentialsDTO) {
        ClienteLoginResponseDTO loginResult = authenticationService.loginCliente(credentialsDTO);
        return ResponseEntity.ok(ApiResponse.success("Usuário autenticado com sucesso", loginResult));
    }

    @Operation(summary = "Login de Operador (Admin)", description = "Autentica um funcionário/administrador via CPF e Senha, retornando os dados de permissão e o token JWT. Rota pública.")
    @PostMapping("/operador-login")
    public ResponseEntity<ApiResponse<OperadorLoginResponseDTO>> loginOperador(@Valid @RequestBody CredenciaisDTO credentialsDTO) {
        OperadorLoginResponseDTO loginResult = authenticationService.loginOperador(credentialsDTO);
        return ResponseEntity.ok(ApiResponse.success("Usuário autenticado com sucesso", loginResult));
    }
}