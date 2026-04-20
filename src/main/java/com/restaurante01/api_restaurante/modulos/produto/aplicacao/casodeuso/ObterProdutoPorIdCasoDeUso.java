package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;

@Service
public class ObterProdutoPorIdCasoDeUso {

    private final ProdutoJpaAdaptador repositorio;
    private final ProdutoMapeador mapeador;

    public ObterProdutoPorIdCasoDeUso(ProdutoJpaAdaptador repositorio,
                                      ProdutoMapeador mapeador) {
        this.repositorio = repositorio;
        this.mapeador = mapeador;
    }

    public Produto retornarEntidade(Long id){
        return  repositorio.findById(id).
                orElseThrow(() -> new ProdutoNaoEncontradoException("O produto não foi localizado no sistema"));
    }
    public ProdutoDTO retornarDTO(Long id){
        return  mapeador.mapearUmaEntidadeParaDTO(retornarEntidade(id));
    }

}
