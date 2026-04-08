package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapper;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.ProdutoRepositorioAdapter;
import org.springframework.stereotype.Service;

@Service
public class ObterProdutoPorIdCasoDeUso {

    private final ProdutoRepositorioAdapter repositorio;
    private final ProdutoMapper mapeador;

    public ObterProdutoPorIdCasoDeUso(ProdutoRepositorioAdapter repositorio,
                                      ProdutoMapper mapeador) {
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
