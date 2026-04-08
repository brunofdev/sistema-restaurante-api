package com.restaurante01.api_restaurante.modulos.produto.api.controlador;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.saida.LoteProdutosResponseDTO;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "2. Produtos", description = "Gerenciamento do estoque e catálogo de produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProdutoController {

    private final CriarProdutoCasoDeUso criarProdutoCasoDeUso;
    private final ObterTodosProdutosCasoDeUso obterTodosProdutosCasoDeUso;
    private final ObterProdutosDisponiveisCasoDeUso obterProdutosDisponiveisCasoDeUso;
    private final ObterProdutosIndisponiveisCasoDeUso obterProdutosIndisponiveisCasoDeUso;
    private final AtualizarProdutosEmLoteCasoDeUso atualizarProdutosEmLoteCasoDeUso;
    private final DeletarProdutoCasoDeUso deletarProdutoCasoDeUso;
    private final AtualizarUmProdutoCasoDeUso atualizarUmProdutoCasoDeUso;
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;
    private final ObterProdutosBaixaQntdCasoDeUso obterProdutosBaixaQntdCasoDeUso;

    public ProdutoController(CriarProdutoCasoDeUso criarProdutoCasoDeUso,
                             ObterTodosProdutosCasoDeUso obterTodosProdutosCasoDeUso,
                             ObterProdutosDisponiveisCasoDeUso obterProdutosDisponiveisCasoDeUso,
                             ObterProdutosIndisponiveisCasoDeUso obterProdutosIndisponiveisCasoDeUso,
                             AtualizarProdutosEmLoteCasoDeUso atualizarLoteProdutos,
                             DeletarProdutoCasoDeUso deletarProdutoCasoDeUso,
                             AtualizarUmProdutoCasoDeUso atualizarUmProdutoCasoDeUso,
                             ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso,
                             ObterProdutosBaixaQntdCasoDeUso obterProdutosBaixaQntdCasoDeUso) {
        this.criarProdutoCasoDeUso = criarProdutoCasoDeUso;
        this.obterTodosProdutosCasoDeUso = obterTodosProdutosCasoDeUso;
        this.obterProdutosDisponiveisCasoDeUso = obterProdutosDisponiveisCasoDeUso;
        this.obterProdutosIndisponiveisCasoDeUso = obterProdutosIndisponiveisCasoDeUso;
        this.atualizarProdutosEmLoteCasoDeUso = atualizarLoteProdutos;
        this.deletarProdutoCasoDeUso = deletarProdutoCasoDeUso;
        this.atualizarUmProdutoCasoDeUso = atualizarUmProdutoCasoDeUso;
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
        this.obterProdutosBaixaQntdCasoDeUso = obterProdutosBaixaQntdCasoDeUso;
    }

    @Operation(summary = "Busca um produto por ID", description = "Retorna os detalhes de um produto específico. Requer ROLE_ADMIN3.")
    @GetMapping("/{idProduto}")
    public ResponseEntity<ApiResponse<ProdutoDTO>> listarUmProduto(@PathVariable("idProduto") long id){
        ProdutoDTO produto = obterProdutoPorIdCasoDeUso.retornarDTO(id);
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", produto));
    }

    @Operation(summary = "Lista todos os produtos", description = "Retorna todos os produtos do sistema. Rota pública.")
    @GetMapping("/todos-produtos")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> listarTodosOsProdutos(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", obterTodosProdutosCasoDeUso.executar()));
    }

    @Operation(summary = "Lista produtos disponíveis", description = "Retorna apenas os produtos marcados como disponíveis. Requer ROLE_ADMIN3.")
    @GetMapping("/produtos-disponiveis")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> listarApenasDisponiveis(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", obterProdutosDisponiveisCasoDeUso.executar()));
    }

    @Operation(summary = "Lista produtos indisponíveis", description = "Retorna apenas os produtos marcados como fora de estoque/indisponíveis. Requer ROLE_ADMIN3.")
    @GetMapping("/produtos-indisponiveis")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> listarIndisponiveis(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado" , obterProdutosIndisponiveisCasoDeUso.executar()));
    }

    @Operation(summary = "Produtos com estoque baixo", description = "Lista produtos com quantidade abaixo do limite de segurança. Requer ROLE_ADMIN3.")
    @GetMapping("produtos-com-baixa-quantidade")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> listarProdutosComBaixaQntd(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", obterProdutosBaixaQntdCasoDeUso.executar()));
    }

    @Operation(summary = "Adiciona novo produto", description = "Cadastra um novo produto no estoque. Requer ROLE_ADMIN1.")
    @PostMapping("/adicionar-produto")
    public ResponseEntity<ApiResponse<ProdutoDTO>> adicionarProduto(@Valid @RequestBody ProdutoCreateDTO produtoDTO){
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , criarProdutoCasoDeUso.executar(produtoDTO)));
    }

    @Operation(summary = "Atualiza um produto", description = "Altera os dados de um produto existente. Requer ROLE_ADMIN3.")
    @PutMapping("/atualizar-um-produto")
    public ResponseEntity<ApiResponse<ProdutoDTO>> atualizarProduto(@Valid @RequestBody  ProdutoDTO produto){
        return  ResponseEntity.ok(ApiResponse.success("Produto atualizado", atualizarUmProdutoCasoDeUso.executar(produto)));
    }

    @Operation(summary = "Atualização em lote", description = "Atualiza múltiplos produtos simultaneamente. Requer ROLE_ADMIN3.")
    @PutMapping("/atualizar-varios-produtos")
    public ResponseEntity<ApiResponse<LoteProdutosResponseDTO>> atualizarProdutos(@Valid @RequestBody List<ProdutoDTO> produtos){
        List<ProdutoDTO> produtoAtualizado = atualizarProdutosEmLoteCasoDeUso.executar(produtos);
        LoteProdutosResponseDTO resposta = new LoteProdutosResponseDTO
                ("Um total de: " + produtoAtualizado.size() + " foram atualizados\n"
                        , produtoAtualizado);
        return  ResponseEntity.ok(ApiResponse.success("Atualização em lote realizada" , resposta));
    }

    @Operation(summary = "Exclui um produto", description = "Remove permanentemente um produto do sistema. Requer ROLE_ADMIN3.")
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<ApiResponse> deletarProduto(@PathVariable long produtoId){
        deletarProdutoCasoDeUso.execute(produtoId);
        return ResponseEntity.ok(ApiResponse.success("Recurso deletado", null));
    }
}