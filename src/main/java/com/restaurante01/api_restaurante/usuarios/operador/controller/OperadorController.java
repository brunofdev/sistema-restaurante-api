package com.restaurante01.api_restaurante.usuarios.operador.controller;

import com.restaurante01.api_restaurante.core.utils.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.usuarios.operador.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.usuarios.operador.service.OperadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/operador")
@Validated
public class OperadorController {

    @Autowired
    private OperadorService operadorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    public ResponseEntity<ApiResponse<OperadorDTO>> cadastrarUsuario(@Valid @RequestBody CadastrarOperadorDTO dto){
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        CadastrarOperadorDTO dtoComSenhaEncoded = dto.withSenha(senhaCriptografada);
        OperadorDTO operadorCriadoDTO = operadorService.cadastrarNovoOperador(dtoComSenhaEncoded);
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , operadorCriadoDTO));
    }
    @GetMapping("/obter-todos")
    public ResponseEntity<ApiResponse<List<OperadorDTO>>> listarUsuarios(){
        return ResponseEntity.ok(ApiResponse.success("Recurso disponivel", operadorService.listarOperadores()));
    }
}
