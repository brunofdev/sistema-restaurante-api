package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.saida.ProdutoCustomDTO;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;


@Setter
@Getter
public class AssociacoesDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Boolean disponibilidade;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    private List<ProdutoCustomDTO> produtos;

    public AssociacoesDTO(){};
    public AssociacoesDTO(Long id, String nome, String descricao, Boolean disponibilidade,
                          LocalDate dataInicio, LocalDate dataFim, List<ProdutoCustomDTO> produtos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.disponibilidade = disponibilidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.produtos = produtos;
    }

}
