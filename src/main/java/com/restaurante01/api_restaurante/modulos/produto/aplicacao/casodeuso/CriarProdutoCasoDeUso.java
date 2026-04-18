package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoCreateDTO;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapper;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.validador.ProdutoValidator;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoRepositorioAdapter;
import org.springframework.stereotype.Service;

@Service
public class CriarProdutoCasoDeUso {

    private final ProdutoRepositorioAdapter repositorio;
    private final ProdutoValidator validador;
    private final ProdutoMapper mappeador;

    public CriarProdutoCasoDeUso(ProdutoRepositorioAdapter repositorio, ProdutoValidator validador, ProdutoMapper mappeador) {
        this.repositorio = repositorio;
        this.validador = validador;
        this.mappeador = mappeador;
    }
    public ProdutoDTO executar(ProdutoCreateDTO produtoCreateDTO) {
        ProdutoDTO produtoDTO = mappeador.mapearProdutoDTO(produtoCreateDTO);
        validador.validarProduto(produtoDTO);
        Produto novoProduto = mappeador.mapearUmaDtoParaEntidade(produtoDTO);
        Produto produtoSalvo = repositorio.save(novoProduto);
        return mappeador.mapearUmaEntidadeParaDTO(produtoSalvo);
    }

}
