package com.restaurante01.api_restaurante.produto;


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
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable long id, @RequestBody Produto produto){
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        return  ResponseEntity.ok(produtoAtualizado);
    }
    @PutMapping("/atualizar-varios-produtos")
    public ResponseEntity<AtualizarLoteProdutosRespostaDTO> atualizarProdutos(@RequestBody List<ProdutoDTO> produtos){
        List<ProdutoDTO> produtoAtualizado = produtoService.atualizarDiversosProdutos(produtos);
        AtualizarLoteProdutosRespostaDTO resposta = new AtualizarLoteProdutosRespostaDTO
                ("Um total de: " + produtoAtualizado.toArray().length + " Foram atualizados\n"
                        , produtoAtualizado);
        return  ResponseEntity.ok(resposta);
    }
    @DeleteMapping("/{id}")
        public ResponseEntity<Produto> deletarProduto(@PathVariable long id){
        Produto produtoDeletado = produtoService.deletarProduto(id);
        return ResponseEntity.ok(produtoDeletado);
    }
}
