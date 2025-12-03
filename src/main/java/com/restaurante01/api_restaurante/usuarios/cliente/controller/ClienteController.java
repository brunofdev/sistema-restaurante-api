package com.restaurante01.api_restaurante.usuarios.cliente.controller;

import com.restaurante01.api_restaurante.core.utils.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.usuarios.dto.entrada.CadastrarUsuarioDTO;
import com.restaurante01.api_restaurante.usuarios.dto.saida.UsuarioDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/cliente")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    public ResponseEntity<ApiResponse<ClienteDTO>> cadastrarUsuario(@Valid @RequestBody CadastrarClienteDTO dto){
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        CadastrarClienteDTO dtoComSenhaEncoded = dto.withSenha(senhaCriptografada);
        ClienteDTO clienteCriadoDTO = clienteService.cadastrarNovoCliente(dtoComSenhaEncoded);
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , clienteCriadoDTO));
    }
    @GetMapping("/obter-todos")
    public ResponseEntity<ApiResponse<List<ClienteDTO>>> listarUsuarios(){
        return ResponseEntity.ok(ApiResponse.success("Recurso disponivel", clienteServiceService.listarClientes()));
    }
}
