package com.restaurante01.api_restaurante.modulos.produto.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoteProdutosRespostaDTO {
    private String mensagem;
    private List<ProdutoDTO> produtos;

    public LoteProdutosRespostaDTO(String mensagem, List<ProdutoDTO> produtos) {
        this.mensagem = mensagem;
        this.produtos = produtos;
    }

}
