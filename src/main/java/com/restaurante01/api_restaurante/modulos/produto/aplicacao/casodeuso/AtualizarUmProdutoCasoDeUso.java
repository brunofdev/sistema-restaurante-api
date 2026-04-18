package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapper;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.validador.ProdutoValidator;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoRepositorioAdapter;
import org.springframework.stereotype.Service;

@Service
public class AtualizarUmProdutoCasoDeUso {

    private final ProdutoValidator validador;
    private final ProdutoMapper mapeador;
    private final ProdutoRepositorioAdapter repositorio;
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    public AtualizarUmProdutoCasoDeUso(ProdutoValidator validador,
                                       ProdutoMapper mapeador,
                                       ProdutoRepositorioAdapter repositorio,
                                       ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso) {
        this.validador = validador;
        this.mapeador = mapeador;
        this.repositorio = repositorio;
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
    }

    public ProdutoDTO executar(ProdutoDTO produtoAtualizado) {
        validador.validarProduto(produtoAtualizado);
        Produto produtoExistente = obterProdutoPorIdCasoDeUso.retornarEntidade(produtoAtualizado.getId());
        mapeador.atualizarProduto(produtoExistente, produtoAtualizado);
        return mapeador.mapearUmaEntidadeParaDTO(repositorio.save(produtoExistente));
    }
}
