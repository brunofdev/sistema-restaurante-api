package com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.porta.ProdutoPorta;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.ObterProdutoPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoCardapioProdutoAdaptador implements ProdutoPorta {

    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    public ProdutoCardapioProdutoAdaptador(ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso) {
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
    }

    @Override
    public Produto obterProdutoPorId(Long id){
        return obterProdutoPorIdCasoDeUso.retornarEntidade(id);
    }
}
