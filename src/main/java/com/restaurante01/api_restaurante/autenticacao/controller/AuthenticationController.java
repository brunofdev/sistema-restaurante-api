package com.restaurante01.api_restaurante.autenticacao.controller;


import com.restaurante01.api_restaurante.autenticacao.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.autenticacao.dto.LoginResponseDTO;
import com.restaurante01.api_restaurante.autenticacao.service.AuthenticationService;
import com.restaurante01.api_restaurante.core.utils.retorno_padrao_api.ApiResponse;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody CredenciaisDTO credentialsDTO) {
        LoginResponseDTO loginResult = authenticationService.login(credentialsDTO);
        return ResponseEntity.ok(ApiResponse.success("Usuário autenticado com sucesso", loginResult));
    }
}
