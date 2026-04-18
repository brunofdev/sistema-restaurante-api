package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public class ObterProdutoValorCostumizadoCasoDeUso {

    private final CardapioProdutoRepositorio repositorio;

    public ObterProdutoValorCostumizadoCasoDeUso(CardapioProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public CardapioProduto executar (Long idCardapio, Long idProduto){
        return repositorio.findByCardapioIdAndProdutoId(idCardapio, idProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com o id: " + idProduto + " pertecente ao cardapio: " + idCardapio));
    }
}
