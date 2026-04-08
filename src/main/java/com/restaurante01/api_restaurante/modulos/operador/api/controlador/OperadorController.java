package com.restaurante01.api_restaurante.modulos.operador.api.controlador;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.aplicacao.casodeuso.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operador")
@CrossOrigin("*")
@Validated
@Tag(name = "9. Operadores", description = "Gerenciamento de usuários administrativos e funcionários")
public class OperadorController {

    private final CadastrarOperadorCasoDeUso cadastrarOperador;
    private final ListarTodosOperadoresCasoDeUso listarOperadores;
    private final AtualizarOperadorCasoDeUso atualizarOperador;
    private final DeletarOperadorCasoDeUso deletarOperador;
    private final PasswordEncoder passwordEncoder;

    public OperadorController(
            CadastrarOperadorCasoDeUso cadastrarOperador,
            ListarTodosOperadoresCasoDeUso listarOperadores,
            AtualizarOperadorCasoDeUso atualizarOperador,
            DeletarOperadorCasoDeUso deletarOperador,
            PasswordEncoder passwordEncoder) {
        this.cadastrarOperador = cadastrarOperador;
        this.listarOperadores = listarOperadores;
        this.atualizarOperador = atualizarOperador;
        this.deletarOperador = deletarOperador;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Cadastrar novo operador", description = "Cria um novo usuário administrativo. Requer ROLE_ADMIN2.")
    @PostMapping("/cadastro")
    public ResponseEntity<ApiResponse<OperadorDTO>> cadastrarUsuario(@Valid @RequestBody CadastrarOperadorDTO dto){
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        CadastrarOperadorDTO dtoComSenhaEncoded = dto.withSenha(senhaCriptografada);

        OperadorDTO operadorCriadoDTO = cadastrarOperador.executar(dtoComSenhaEncoded);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Recurso criado" , operadorCriadoDTO));
    }

    @Operation(summary = "Listar todos os operadores", description = "Retorna uma lista paginada de operadores. Requer ROLE_ADMIN2.")
    @GetMapping("/obter-todos")
    public ResponseEntity<ApiResponse<Page<OperadorDTO>>> listarUsuarios(
            @ParameterObject @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.success("Recurso disponível", listarOperadores.executar(pageable)));
    }

    @Operation(summary = "Atualizar operador", description = "Edita as informações de um operador existente. Requer ROLE_ADMIN2.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OperadorDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody OperadorDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Recurso atualizado", atualizarOperador.executar(id, dto)));
    }

    @Operation(summary = "Deletar operador", description = "Remove um operador permanentemente do sistema. Requer ROLE_ADMIN3.")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        deletarOperador.executar(id);
        return ResponseEntity.ok(ApiResponse.success("Recurso deletado", null));
    }
}