package com.restaurante01.api_restaurante.modulos.cardapio.api.controlador.privado;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacaoFeitaRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.AssociarProdutoAoCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.AtualizarCamposCustomDaAssociacaoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.DesassociarProdutoDoCardapioCasoDeUso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cardapio-produto/operador")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "-> Cardápio & Produto - Gerência", description = "Gerenciamento de vínculos entre produtos e cardápios")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMIN1', 'ADMIN2', 'ADMIN3')")
public class AssociacaoOperadorControlador {

    private final AssociarProdutoAoCardapioCasoDeUso associarProduto;
    private final AtualizarCamposCustomDaAssociacaoCasoDeUso atualizarCamposCustom;
    private final DesassociarProdutoDoCardapioCasoDeUso desassociarProduto;

    public AssociacaoOperadorControlador(
            AssociarProdutoAoCardapioCasoDeUso associarProduto,
            AtualizarCamposCustomDaAssociacaoCasoDeUso atualizarCamposCustom,
            DesassociarProdutoDoCardapioCasoDeUso desassociarProduto) {
        this.associarProduto = associarProduto;
        this.atualizarCamposCustom = atualizarCamposCustom;
        this.desassociarProduto = desassociarProduto;
    }

    @Operation(summary = "Associar produto ao cardápio", description = "Cria um vínculo entre um produto e um cardápio.")
    @PostMapping("/associar")
    public ResponseEntity<ApiResponse<AssociacaoFeitaRespostaDTO>> associarProdutoAoCardapio(@RequestBody @Valid AssociacaoEntradaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Recurso criado", associarProduto.executar(dto)));
    }

    @Operation(summary = "Atualizar campos customizados", description = "Permite editar informações específicas da ligação.")
    @PutMapping("/atualizar")
    public ResponseEntity<ApiResponse<AssociacaoFeitaRespostaDTO>> atualizarCamposCustomDaAssociacao(@RequestBody @Valid AssociacaoEntradaDTO dto){
        return ResponseEntity.ok(ApiResponse.success("Recurso atualizado", atualizarCamposCustom.executar(dto)));
    }

    @Operation(summary = "Desassociar produto do cardápio", description = "Remove um produto de um cardápio específico.")
    @DeleteMapping("/cardapio/{idCardapio}/produto/{idProduto}")
    public ResponseEntity<ApiResponse<Void>> deletarAssociacaoCardapioProduto(@PathVariable @Min(1) long idCardapio, @PathVariable @Min(1) long idProduto){
        desassociarProduto.executar(idCardapio, idProduto);
        return ResponseEntity.ok(ApiResponse.success("Recurso deletado", null));
    }
}