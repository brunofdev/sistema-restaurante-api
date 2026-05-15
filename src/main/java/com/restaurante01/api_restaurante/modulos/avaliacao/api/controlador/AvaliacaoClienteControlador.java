package com.restaurante01.api_restaurante.modulos.avaliacao.api.controlador;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoPendenteClienteDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.BuscarAvaliacoesPendentesClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao/cliente")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "-> Avaliacao - Cliente", description = "Ações exclusivas do aplicativo do cliente para manipular avaliações")
@SecurityRequirement(name = "bearerAuth")
public class AvaliacaoClienteControlador {

    private final BuscarAvaliacoesPendentesClienteCasoDeUso buscarAvaliacoesPendentes;

    public AvaliacaoClienteControlador(BuscarAvaliacoesPendentesClienteCasoDeUso buscarAvaliacoesPendentes) {
        this.buscarAvaliacoesPendentes = buscarAvaliacoesPendentes;
    }

    @Operation(summary = "Listar avaliações pendentes", description = "Retorna todas as avaliações com status DISPONIVEL do cliente autenticado.")
    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<AvaliacaoPendenteClienteDTO>>> listarPendentes(
            @AuthenticationPrincipal Usuario usuarioLogado) {

        if (usuarioLogado instanceof Cliente clienteLogado) {
            List<AvaliacaoPendenteClienteDTO> avaliacoes = buscarAvaliacoesPendentes.executar(clienteLogado.getId());
            return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", avaliacoes));
        }
        throw new AccessDeniedException("Operadores não possuem avaliações pendentes.");
    }
}
