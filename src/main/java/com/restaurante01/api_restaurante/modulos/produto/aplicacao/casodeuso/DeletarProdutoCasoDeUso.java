package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;

@Service
public class DeletarProdutoCasoDeUso {

    private final ProdutoJpaAdaptador repositorio;

    public DeletarProdutoCasoDeUso(ProdutoJpaAdaptador repositorio) {
        this.repositorio = repositorio;
    }

    public void execute(long id) {
        Produto produto = repositorio.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com id " + id + " não encontrado"));
        repositorio.delete(produto);
    }
}
