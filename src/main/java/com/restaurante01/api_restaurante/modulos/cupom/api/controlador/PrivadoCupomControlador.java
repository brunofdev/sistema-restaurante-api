package com.restaurante01.api_restaurante.modulos.cupom.api.controlador;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CriarCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CupomAtualizadoDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.AtualizarCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.CriarCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.DeletarCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.ListarTodosCuponsCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupom/admin")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "-> Cupom - Administradores", description = "Ações exclusivas da gestão de cupons promocionais")
@SecurityRequirement(name = "bearerAuth")
public class PrivadoCupomControlador {

    private final CriarCupomCasoDeUso criarCupom;
    private final AtualizarCupomCasoDeUso atualizarCupom;
    private final DeletarCupomCasoDeUso deletarCupom;
    private final ListarTodosCuponsCasoDeUso listarTodosCupons;

    public PrivadoCupomControlador(
            CriarCupomCasoDeUso criarCupom,
            AtualizarCupomCasoDeUso atualizarCupom,
            DeletarCupomCasoDeUso deletarCupom,
            ListarTodosCuponsCasoDeUso listarTodosCupons) {
        this.criarCupom = criarCupom;
        this.atualizarCupom = atualizarCupom;
        this.deletarCupom = deletarCupom;
        this.listarTodosCupons = listarTodosCupons;
    }

    @Operation(summary = "Criar um novo cupom", description = "Registra um novo cupom de desconto no sistema.")
    @PostMapping("/criar")
    public ResponseEntity<ApiResponse<CupomAdminDTO>> criar(
            @Validated @RequestBody CriarCupomDTO dto) {
        CupomAdminDTO novoCupom = criarCupom.executar(dto);
        return ResponseEntity.ok(ApiResponse.success("Cupom criado com sucesso", novoCupom));
    }

    @Operation(summary = "Listar todos os cupons", description = "Retorna uma lista contendo todos os cupons registrados no banco de dados.")
    @GetMapping("/listar")
    public ResponseEntity<ApiResponse<List<Cupom>>> listarTodos() {
        List<Cupom> listaCupons = listarTodosCupons.executar();
        return ResponseEntity.ok(ApiResponse.success("Cupons obtidos com sucesso", listaCupons));
    }

    @Operation(summary = "Atualizar cupom existente", description = "Atualiza os dados de um cupom específico baseado no seu ID.")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ApiResponse<CupomAdminDTO>> atualizar(
            @PathVariable Long id,
            @Validated @RequestBody CupomAtualizadoDTO dto) {
        CupomAdminDTO cupomAtualizado = atualizarCupom.executar(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Cupom atualizado com sucesso", cupomAtualizado));
    }

    @Operation(summary = "Deletar cupom", description = "Remove permanentemente um cupom do sistema.")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        deletarCupom.executar(id);
        return ResponseEntity.ok(ApiResponse.success("Cupom deletado com sucesso", null));
    }
}