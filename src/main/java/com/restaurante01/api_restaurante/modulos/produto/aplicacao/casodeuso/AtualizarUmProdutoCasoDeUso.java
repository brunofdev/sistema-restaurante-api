package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.mapeador.ProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.validador.ProdutoValidador;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;

@Service
public class AtualizarUmProdutoCasoDeUso {

    private final ProdutoValidador validador;
    private final ProdutoMapeador mapeador;
    private final ProdutoJpaAdaptador repositorio;
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    public AtualizarUmProdutoCasoDeUso(ProdutoValidador validador,
                                       ProdutoMapeador mapeador,
                                       ProdutoJpaAdaptador repositorio,
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
