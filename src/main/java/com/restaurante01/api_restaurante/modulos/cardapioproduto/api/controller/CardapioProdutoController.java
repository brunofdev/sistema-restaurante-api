package com.restaurante01.api_restaurante.modulos.cardapioproduto.api.controller;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapioproduto")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "Associação Cardápio & Produto", description = "Endpoints para gerenciar quais produtos pertencem a quais cardápios")
@SecurityRequirement(name = "bearerAuth")
public class CardapioProdutoController {

    private final ListarTodasAssociacoesCasoDeUso listarTodasAssociacoes;
    private final ListarAssociacaoPorCardapioIdCasoDeUso listarPorCardapioId;
    private final AssociarProdutoAoCardapioCasoDeUso associarProduto;
    private final AtualizarCamposCustomDaAssociacaoCasoDeUso atualizarCamposCustom;
    private final DesassociarProdutoDoCardapioCasoDeUso desassociarProduto;

    public CardapioProdutoController(
            ListarTodasAssociacoesCasoDeUso listarTodasAssociacoes,
            ListarAssociacaoPorCardapioIdCasoDeUso listarPorCardapioId,
            AssociarProdutoAoCardapioCasoDeUso associarProduto,
            AtualizarCamposCustomDaAssociacaoCasoDeUso atualizarCamposCustom,
            DesassociarProdutoDoCardapioCasoDeUso desassociarProduto) {
        this.listarTodasAssociacoes = listarTodasAssociacoes;
        this.listarPorCardapioId = listarPorCardapioId;
        this.associarProduto = associarProduto;
        this.atualizarCamposCustom = atualizarCamposCustom;
        this.desassociarProduto = desassociarProduto;
    }

    @Operation(summary = "Listar todas as associações", description = "Retorna todos os cardápios com seus respectivos produtos. Rota pública.")
    @GetMapping("/obter-todas-associacoes")
    public ResponseEntity<ApiResponse<List<CardapioComListaProdutoDTO>>> obterTodasAssociacoes() {
        return ResponseEntity.ok(ApiResponse.success("Recurso disponibilizado", listarTodasAssociacoes.executar()));
    }

    @Operation(summary = "Obter produtos de um cardápio", description = "Retorna a associação de produtos de um cardápio específico via ID. Rota pública.")
    @GetMapping("/cardapio/{idCardapio}")
    public ResponseEntity<ApiResponse<CardapioProdutoDTO>> obterAssociacaoPorCardapioId(@PathVariable @Min(1) long idCardapio){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", listarPorCardapioId.executar(idCardapio)));
    }

    @Operation(summary = "Associar produto ao cardápio", description = "Cria um vínculo entre um produto e um cardápio. Requer ROLE_ADMIN1.")
    @PostMapping("/associar-cardapioproduto")
    public ResponseEntity<ApiResponse<CardapioProdutoAssociacaoRespostaDTO>> associarProdutoAoCardapio(@RequestBody @Valid CardapioProdutoAssociacaoEntradaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Recurso criado", associarProduto.executar(dto)));
    }

    @Operation(summary = "Atualizar campos customizados", description = "Permite editar informações específicas da ligação. Requer ROLE_ADMIN1.")
    @PutMapping("/atualizar-campos-custom")
    public ResponseEntity<ApiResponse<CardapioProdutoAssociacaoRespostaDTO>> atualizarCamposCustomDaAssociacao(@RequestBody @Valid CardapioProdutoAssociacaoEntradaDTO dto){
        return ResponseEntity.ok(ApiResponse.success("Recurso atualizado", atualizarCamposCustom.executar(dto)));
    }

    @Operation(summary = "Desassociar produto do cardápio", description = "Remove um produto de um cardápio específico. Requer ROLE_ADMIN2.")
    @DeleteMapping("/cardapio/{idCardapio}/produto/{idProduto}")
    public ResponseEntity<ApiResponse<Void>> deletarAssociacaoCardapioProduto(@PathVariable @Min(1) long idCardapio, @PathVariable @Min(1) long idProduto){
        desassociarProduto.executar(idCardapio, idProduto);
        return ResponseEntity.ok(ApiResponse.success("Recurso deletado", null));
    }
}