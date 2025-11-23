package com.restaurante01.api_restaurante.usuarios.controller;

import com.restaurante01.api_restaurante.core.utils.ApiResponse;
import com.restaurante01.api_restaurante.usuarios.dto.entrada.CadastrarUsuarioDTO;
import com.restaurante01.api_restaurante.usuarios.dto.saida.UsuarioDTO;
import com.restaurante01.api_restaurante.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    public ResponseEntity<ApiResponse<UsuarioDTO>> cadastrarUsuario(@Valid @RequestBody CadastrarUsuarioDTO dto){
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        CadastrarUsuarioDTO dtoComSenhaEncoded = dto.withSenha(senhaCriptografada);
        UsuarioDTO usuarioCriadoDTO = usuarioService.cadastrarNovoUsuario(dtoComSenhaEncoded);
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , usuarioCriadoDTO));
    }
    @GetMapping("/obter-todos")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarUsuarios(){
        return ResponseEntity.ok(ApiResponse.success("Recurso disponivel", usuarioService.listarUsuarios()));
    }
    //endpoint para teste apenas
    @PostMapping("/cadastro-admin1")
    public ResponseEntity<ApiResponse<UsuarioDTO>> cadastrarUsuarioAdmin1(@Valid @RequestBody CadastrarUsuarioDTO dto){
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        CadastrarUsuarioDTO dtoComSenhaEncoded = dto.withSenha(senhaCriptografada);
        UsuarioDTO usuarioCriadoDTO = usuarioService.cadastrarNovoUsuarioAdmin1(dtoComSenhaEncoded);
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , usuarioCriadoDTO));
    }
    //endpoint para teste apenas
    @PostMapping("/cadastro-admin3")
    public ResponseEntity<ApiResponse<UsuarioDTO>> cadastrarUsuarioAdmin3(@Valid @RequestBody CadastrarUsuarioDTO dto){
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        CadastrarUsuarioDTO dtoComSenhaEncoded = dto.withSenha(senhaCriptografada);
        UsuarioDTO usuarioCriadoDTO = usuarioService.cadastrarNovoUsuarioAdmin3(dtoComSenhaEncoded);
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , usuarioCriadoDTO));
    }
}
