package com.restaurante01.api_restaurante.modulos.produto.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;

import java.util.List;

public class LoteProdutosRespostaDTO {
    private String mensagem;
    private List<ProdutoDTO> produtos;

    public LoteProdutosRespostaDTO(String mensagem, List<ProdutoDTO> produtos) {
        this.mensagem = mensagem;
        this.produtos = produtos;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }
}
