package com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.porta.associacao.ProdutoPorta;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.ObterProdutoPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoAssociacaoAdaptador implements ProdutoPorta {

    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    public ProdutoAssociacaoAdaptador(ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso) {
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
    }

    @Override
    public Produto obterProdutoPorId(Long id){
        return obterProdutoPorIdCasoDeUso.retornarEntidade(id);
    }
}
