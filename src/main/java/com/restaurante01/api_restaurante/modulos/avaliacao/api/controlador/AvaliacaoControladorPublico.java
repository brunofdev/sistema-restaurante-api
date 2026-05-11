package com.restaurante01.api_restaurante.modulos.avaliacao.api.controlador;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
        ENDPOINT E CONTROLADOR CRIADO POR ENQUANTO APENAS PARA TESTES, AINDA NÃO ESTA ADEQUADO AO PADRÃO DO SISTEMA
 */

@RestController
@RequestMapping("/avaliacao/cliente")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "-> Avaliacao - Cliente", description = "Ações exclusivas do aplicativo do cliente para manipular avaliacoes")
@SecurityRequirement(name = "bearerAuth")
public class AvaliacaoControladorPublico {

    private final AvaliacaoRepositorio avaliacaoRepositorio;

    public AvaliacaoControladorPublico(AvaliacaoRepositorio avaliacaoRepositorio) {
        this.avaliacaoRepositorio = avaliacaoRepositorio;
    }

    @Operation(summary = "Listar pedidos do cliente logado", description = "Retorna o histórico de pedidos paginado do cliente autenticado.")
    @GetMapping("/listartodos")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<Avaliacao>>> listarAvaliacoes(
            @AuthenticationPrincipal Usuario usuarioLogado) {
        List<Avaliacao> avaliacoes = avaliacaoRepositorio.buscarTodos();
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado",  avaliacoes));
    }
}