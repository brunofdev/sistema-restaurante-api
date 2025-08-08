package com.restaurante01.api_restaurante.produto.controller;


import com.restaurante01.api_restaurante.produto.dto.LoteProdutosResponseDTO;
import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.service.ProdutoService;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produtos")
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
    public ResponseEntity<ProdutoDTO> adicionarProduto(@RequestBody ProdutoDTO produtoDTO){
        return ResponseEntity.ok(produtoService.adicionarNovoProduto(produtoDTO));
    }
    @PutMapping("/atualizar-um-produto/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable long id, @RequestBody ProdutoDTO produto){
        ProdutoDTO produtoAtualizado = produtoService.atualizarProduto(id, produto);
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
    @DeleteMapping("/{id}")
        public ResponseEntity<ProdutoDTO> deletarProduto(@PathVariable long id){
        ProdutoDTO produtoDeletado = produtoService.deletarProduto(id);
        return ResponseEntity.ok(produtoDeletado);
    }
}
