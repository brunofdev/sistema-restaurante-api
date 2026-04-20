package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.CriarProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.validador.ProdutoValidador;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;

@Service
public class CriarProdutoCasoDeUso {

    private final ProdutoJpaAdaptador repositorio;
    private final ProdutoValidador validador;
    private final ProdutoMapeador mappeador;

    public CriarProdutoCasoDeUso(ProdutoJpaAdaptador repositorio, ProdutoValidador validador, ProdutoMapeador mappeador) {
        this.repositorio = repositorio;
        this.validador = validador;
        this.mappeador = mappeador;
    }
    public ProdutoDTO executar(CriarProdutoDTO criarProdutoDTO) {
        ProdutoDTO produtoDTO = mappeador.mapearProdutoDTO(criarProdutoDTO);
        validador.validarProduto(produtoDTO);
        Produto novoProduto = mappeador.mapearUmaDtoParaEntidade(produtoDTO);
        Produto produtoSalvo = repositorio.save(novoProduto);
        return mappeador.mapearUmaEntidadeParaDTO(produtoSalvo);
    }

}
