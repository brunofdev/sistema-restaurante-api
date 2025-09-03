package com.restaurante01.api_restaurante.produto.controller;


import com.restaurante01.api_restaurante.produto.dto.saida.LoteProdutosResponseDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produtos")
@Validated
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/todos-produtos")
    public ResponseEntity<List<ProdutoDTO>> listarTodosOsProdutos(){
        return ResponseEntity.ok(produtoService.listarTodosProdutos());
    }
    @GetMapping("/produtos-disponiveis")
    public ResponseEntity<List<ProdutoDTO>> listarApenasDisponiveis(){
        return ResponseEntity.ok(produtoService.listarProdutosDisponiveis());
    }
    @GetMapping("/produtos-indisponiveis")
    public ResponseEntity<List<ProdutoDTO>> listarIndisponiveis(){
        return ResponseEntity.ok(produtoService.listarProdutosIndisponiveis());
    }
    @GetMapping("produtos-com-baixa-quantidade")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosComBaixaQntd(){
        return ResponseEntity.ok(produtoService.listarProdutosComQntdBaixa());
    }
    @PostMapping("/adicionar-produto")
    public ResponseEntity<ProdutoDTO> adicionarProduto(@RequestBody ProdutoCreateDTO produtoDTO){
        return ResponseEntity.ok(produtoService.adicionarNovoProduto(produtoDTO));
    }
    @PutMapping("/atualizar-um-produto")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@RequestBody ProdutoDTO produto){
        ProdutoDTO produtoAtualizado = produtoService.atualizarProduto(produto);
        return  ResponseEntity.ok(produtoAtualizado);
    }
    @PutMapping("/atualizar-varios-produtos")
    public ResponseEntity<LoteProdutosResponseDTO> atualizarProdutos(@RequestBody List<ProdutoDTO> produtos){
        List<ProdutoDTO> produtoAtualizado = produtoService.atualizarLoteProdutos(produtos);
        LoteProdutosResponseDTO resposta = new LoteProdutosResponseDTO
                ("Um total de: " + produtoAtualizado.toArray().length + " Foram atualizados\n"
                        , produtoAtualizado);
        return  ResponseEntity.ok(resposta);
    }
    @DeleteMapping("/{produtoId}")
        public ResponseEntity<Void> deletarProduto(@PathVariable long produtoId){
        produtoService.deletarProduto(produtoId);
        return ResponseEntity.noContent().build();


    }
}
