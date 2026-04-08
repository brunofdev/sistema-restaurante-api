package com.restaurante01.api_restaurante.infraestrutura.autenticacao.controller;


import com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto.ClienteLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto.OperadorLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.AuthenticationService;
import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/cliente-login")
    public ResponseEntity<ApiResponse<ClienteLoginResponseDTO>> loginCliente(@Valid @RequestBody CredenciaisDTO credentialsDTO) {
        ClienteLoginResponseDTO loginResult = authenticationService.loginCliente(credentialsDTO);
        return ResponseEntity.ok(ApiResponse.success("Usuário autenticado com sucesso", loginResult));
    }
    @PostMapping("/operador-login")
    public ResponseEntity<ApiResponse<OperadorLoginResponseDTO>> loginOperador(@Valid @RequestBody CredenciaisDTO credentialsDTO) {
        OperadorLoginResponseDTO loginResult = authenticationService.loginOperador(credentialsDTO);
        return ResponseEntity.ok(ApiResponse.success("Usuário autenticado com sucesso", loginResult));
    }
}
