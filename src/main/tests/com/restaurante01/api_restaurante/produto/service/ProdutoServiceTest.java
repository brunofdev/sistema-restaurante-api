package com.restaurante01.api_restaurante.produto.service;

import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;
import com.restaurante01.api_restaurante.produto.service.ProdutoService;
import com.restaurante01.api_restaurante.produto.validator.ProdutoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ProdutoServiceTest {

    private final ProdutoService produtoService;

    public ProdutoServiceTest(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @RepeatedTest(3)
    @DisplayName("Verifica se um produto está sendo salvo corretamente")
    public void testarSeProdutoSalvaCorretamente(){
        ProdutoRepository produtoRepository;
        ProdutoValidator produtoValidator;
        ProdutoDTO produtoDTO;
        Produto produto;
        ProdutoCreateDTO produtoCreateDTO = new ProdutoCreateDTO(null, null, null, null, null);
        produtoService.adicionarNovoProduto(produtoCreateDTO);
    }

}
