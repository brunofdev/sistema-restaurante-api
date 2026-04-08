package com.restaurante01.api_restaurante.modulos.cardapio.api.controlador;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.CardapioCreateDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.AtualizarUmCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.CriarCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.DeletarUmCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.ObterTodosCardapiosCasoDeUso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
@CrossOrigin(origins = "*")
@Tag(name = "3. Cardápio", description = "Endpoints para gerenciamento do cardápio do restaurante")
@SecurityRequirement(name = "bearerAuth")
public class CardapioController {

    private final CriarCardapioCasoDeUso criarCardapioCasoDeUso;
    private final ObterTodosCardapiosCasoDeUso obterTodosCardapiosCasoDeUso;
    private final AtualizarUmCardapioCasoDeUso atualizarUmCardapioCasoDeUso;
    private final DeletarUmCardapioCasoDeUso deletarUmCardapioCasoDeUso;

    public CardapioController(CriarCardapioCasoDeUso criarCardapioCasoDeUso,
                              ObterTodosCardapiosCasoDeUso obterTodosCardapiosCasoDeUso,
                              AtualizarUmCardapioCasoDeUso atualizarUmCardapioCasoDeUso,
                              DeletarUmCardapioCasoDeUso deletarUmCardapioCasoDeUso) {
        this.criarCardapioCasoDeUso = criarCardapioCasoDeUso;
        this.obterTodosCardapiosCasoDeUso = obterTodosCardapiosCasoDeUso;
        this.atualizarUmCardapioCasoDeUso = atualizarUmCardapioCasoDeUso;
        this.deletarUmCardapioCasoDeUso = deletarUmCardapioCasoDeUso;
    }

    @Operation(summary = "Lista todos os cardápios", description = "Retorna uma lista de todos os cardápios cadastrados. Requer ROLE_ADMIN3.")
    @GetMapping("/listar-todos")
    public ResponseEntity<ApiResponse<List<CardapioDTO>>> solicitarCardapios(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado" , obterTodosCardapiosCasoDeUso.executar()));
    }

    @Operation(summary = "Cria um novo cardápio", description = "Cadastra um novo cardápio no sistema. Requer ROLE_ADMIN3.")
    @PostMapping("/adicionar-novo")
    public ResponseEntity<ApiResponse<CardapioDTO>> adicionarNovoCardapio(@Valid @RequestBody CardapioCreateDTO cardapioDTO){
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , criarCardapioCasoDeUso.executar(cardapioDTO)));
    }

    @Operation(summary = "Atualiza um cardápio existente", description = "Altera os dados de um cardápio já cadastrado. Requer ROLE_ADMIN3.")
    @PutMapping("/atualizar-um")
    public ResponseEntity<ApiResponse<CardapioDTO>> atualizarCardapio(@Valid @RequestBody CardapioDTO cardapioDTO){
        return ResponseEntity.ok(ApiResponse.success("Recurso atualizado", atualizarUmCardapioCasoDeUso.executar(cardapioDTO)));
    }

    @Operation(summary = "Remove um cardápio", description = "Deleta um cardápio do sistema através do ID. Requer ROLE_ADMIN3.")
    @DeleteMapping("{cardapioId}")
    public ResponseEntity<ApiResponse> deletarCardapio(@PathVariable("cardapioId") Long cardapioId){
        deletarUmCardapioCasoDeUso.executar(cardapioId);
        return ResponseEntity.ok(ApiResponse.success("Recurso removido", null));
    }
}