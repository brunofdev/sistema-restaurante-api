package com.restaurante01.api_restaurante.produto;

import java.util.List;

public class AtualizarLoteProdutosRespostaDTO {
    private String mensagem;
    private List<ProdutoDTO> produtos;

    public AtualizarLoteProdutosRespostaDTO(String mensagem, List<ProdutoDTO> produtos) {
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
