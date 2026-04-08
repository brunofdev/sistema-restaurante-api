package com.restaurante01.api_restaurante.modulos.cliente.api.controller;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.aplicacao.servico.ClienteService;
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
        return ResponseEntity.ok(ApiResponse.success("Recurso disponivel", clienteService.listarClientes()));
    }
}
