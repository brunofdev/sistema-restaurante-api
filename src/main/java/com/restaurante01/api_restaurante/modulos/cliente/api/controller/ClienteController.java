package com.restaurante01.api_restaurante.modulos.cliente.api.controller;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.aplicacao.casodeuso.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/cliente")
@Validated
@Tag(name = "Clientes", description = "Gerenciamento de usuários do tipo Cliente e fidelidade")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    private final CadastrarClienteCasoDeUso cadastrarCliente;
    private final ListarTodosClientesCasoDeUso listarClientes;
    private final AtualizarClienteCasoDeUso atualizarCliente;
    private final DeletarClienteCasoDeUso deletarCliente;

    public ClienteController(
            CadastrarClienteCasoDeUso cadastrarCliente,
            ListarTodosClientesCasoDeUso listarClientes,
            AtualizarClienteCasoDeUso atualizarCliente,
            DeletarClienteCasoDeUso deletarCliente) {
        this.cadastrarCliente = cadastrarCliente;
        this.listarClientes = listarClientes;
        this.atualizarCliente = atualizarCliente;
        this.deletarCliente = deletarCliente;
    }

    @Operation(summary = "Cadastrar novo cliente", description = "Cria um novo perfil de cliente no sistema. Rota pública para permitir auto-cadastro.")
    @PostMapping("/cadastro")
    public ResponseEntity<ApiResponse<ClienteDTO>> cadastrarUsuario(@Valid @RequestBody CadastrarClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Recurso criado", cadastrarCliente.executar(dto)));
    }

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista paginada de clientes. Requer ROLE_ADMIN3.")
    @GetMapping("/obter-todos")
    public ResponseEntity<ApiResponse<Page<ClienteDTO>>> listarUsuarios(
            @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Recurso disponível", listarClientes.executar(pageable)));
    }

    @Operation(summary = "Atualizar dados do cliente", description = "Permite alterar informações cadastrais do cliente. Requer ROLE_ADMIN3.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Recurso atualizado", atualizarCliente.executar(id, dto)));
    }

    @Operation(summary = "Remover cliente", description = "Exclui permanentemente um cliente do sistema. Requer ROLE_ADMIN3.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        deletarCliente.executar(id);
        return ResponseEntity.ok(ApiResponse.success("Recurso deletado", null));
    }
}