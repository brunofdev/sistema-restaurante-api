package com.restaurante01.api_restaurante.modulos.cardapio.api.controlador.publico;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacoesDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.ListarAssociacaoPorCardapioIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.ListarTodasAssociacoesCasoDeUso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio-produto/publico")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "4. Cardápio & Produto - Vitrine Pública", description = "Endpoints abertos para visitantes visualizarem os cardápios")
public class CardapioProdutoPublicoControlador {

    private final ListarTodasAssociacoesCasoDeUso listarTodasAssociacoes;
    private final ListarAssociacaoPorCardapioIdCasoDeUso listarPorCardapioId;

    public CardapioProdutoPublicoControlador(
            ListarTodasAssociacoesCasoDeUso listarTodasAssociacoes,
            ListarAssociacaoPorCardapioIdCasoDeUso listarPorCardapioId) {
        this.listarTodasAssociacoes = listarTodasAssociacoes;
        this.listarPorCardapioId = listarPorCardapioId;
    }

    @Operation(summary = "Listar todas as associações", description = "Retorna todos os cardápios com seus respectivos produtos. Rota pública.")
    @GetMapping("/todas")
    public ResponseEntity<ApiResponse<List<AssociacoesDTO>>> obterTodasAssociacoes() {
        return ResponseEntity.ok(ApiResponse.success("Recurso disponibilizado", listarTodasAssociacoes.executar()));
    }

    @Operation(summary = "Obter produtos de um cardápio", description = "Retorna a associação de produtos de um cardápio específico. Rota pública.")
    @GetMapping("/cardapio/{idCardapio}")
    public ResponseEntity<ApiResponse<AssociacaoDTO>> obterAssociacaoPorCardapioId(@PathVariable @Min(1) long idCardapio){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", listarPorCardapioId.executar(idCardapio)));
    }
}