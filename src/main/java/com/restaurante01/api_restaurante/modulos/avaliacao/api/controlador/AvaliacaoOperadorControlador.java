package com.restaurante01.api_restaurante.modulos.avaliacao.api.controlador;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.ConsultaAvaliacoesPendentesCasoDeUso;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.ConsultaTodasAvaliacoesCasoDeUso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao/operador")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "-> Avaliação - Operador", description = "Consultas administrativas sobre avaliações de clientes")
@SecurityRequirement(name = "bearerAuth")
public class AvaliacaoOperadorControlador {

    private final ConsultaTodasAvaliacoesCasoDeUso consultaTodasAvaliacoes;
    private final ConsultaAvaliacoesPendentesCasoDeUso consultaAvaliacoesPendentes;

    public AvaliacaoOperadorControlador(ConsultaTodasAvaliacoesCasoDeUso consultaTodasAvaliacoes,
                                        ConsultaAvaliacoesPendentesCasoDeUso consultaAvaliacoesPendentes) {
        this.consultaTodasAvaliacoes = consultaTodasAvaliacoes;
        this.consultaAvaliacoesPendentes = consultaAvaliacoesPendentes;
    }

    @Operation(
            summary = "Listar todas as avaliações",
            description = "Retorna todas as avaliações registradas no sistema, em qualquer status. Requer ROLE_ADMIN1."
    )
    @GetMapping("/todas")
    public ResponseEntity<ApiResponse<List<AvaliacaoDTO>>> listarTodas() {
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", consultaTodasAvaliacoes.executar()));
    }

    @Operation(
            summary = "Listar avaliações pendentes",
            description = "Retorna todas as avaliações com status PENDENTE, ou seja, ainda aguardando envio ao cliente. Requer ROLE_ADMIN1."
    )
    @GetMapping("/pendentes")
    public ResponseEntity<ApiResponse<List<AvaliacaoDTO>>> listarPendentes() {
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", consultaAvaliacoesPendentes.executar()));
    }
}
