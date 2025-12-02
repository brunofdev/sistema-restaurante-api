package com.restaurante01.api_restaurante.produto.controller;
import com.restaurante01.api_restaurante.core.utils.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.produto.dto.saida.LoteProdutosResponseDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.service.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produtos")
@Validated
@SecurityRequirement(name = "bearerAuth")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{idProduto}")
    public ResponseEntity<ApiResponse<ProdutoDTO>> listarUmProduto(@PathVariable("idProduto") long id){
        ProdutoDTO produto = produtoService.listarUmProdutoPorId(id);
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", produto));
    }
    @GetMapping("/todos-produtos")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> listarTodosOsProdutos(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", produtoService.listarTodosProdutos()));
    }
    @GetMapping("/produtos-disponiveis")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> listarApenasDisponiveis(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", produtoService.listarProdutosDisponiveis()));
    }
    @GetMapping("/produtos-indisponiveis")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> listarIndisponiveis(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado" , produtoService.listarProdutosIndisponiveis()));
    }
    @GetMapping("produtos-com-baixa-quantidade")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> listarProdutosComBaixaQntd(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", produtoService.listarProdutosComQntdBaixa()));
    }
    @PostMapping("/adicionar-produto")
    public ResponseEntity<ApiResponse<ProdutoDTO>> adicionarProduto(@Valid @RequestBody ProdutoCreateDTO produtoDTO){
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , produtoService.adicionarNovoProduto(produtoDTO)));
    }
    @PutMapping("/atualizar-um-produto")
    public ResponseEntity<ApiResponse<ProdutoDTO>> atualizarProduto(@Valid @RequestBody  ProdutoDTO produto){
        return  ResponseEntity.ok(ApiResponse.success("Produto atualizado", produtoService.atualizarProduto(produto)));
    }
    @PutMapping("/atualizar-varios-produtos")
    public ResponseEntity<ApiResponse<LoteProdutosResponseDTO>> atualizarProdutos(@Valid @RequestBody List<ProdutoDTO> produtos){
        List<ProdutoDTO> produtoAtualizado = produtoService.atualizarLoteProdutos(produtos);
        LoteProdutosResponseDTO resposta = new LoteProdutosResponseDTO
                ("Um total de: " + produtoAtualizado.toArray().length + " Foram atualizados\n"
                        , produtoAtualizado);
        return  ResponseEntity.ok(ApiResponse.success("Atualização em lote realizada" , resposta));
    }
    @DeleteMapping("/{produtoId}")
        public ResponseEntity<ApiResponse> deletarProduto(@PathVariable long produtoId){
        produtoService.deletarProduto(produtoId);
        return ResponseEntity.ok(ApiResponse.success("Recurso deletado", null));


    }
}
