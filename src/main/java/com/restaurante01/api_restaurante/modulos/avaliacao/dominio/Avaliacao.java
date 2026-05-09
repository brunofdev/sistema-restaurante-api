package com.restaurante01.api_restaurante.modulos.avaliacao.dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "avaliacoes")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    @Embeddable
    private NotaAvaliacao nota;
    @Embeddable
    private ComentarioAvaliacao comentarioAvaliacao;
    @Enumerated(EnumType.STRING)
    private StatusAvaliacao status;
    @Enumerated(EnumType.STRING)
    private ClassificacaoAvaliacao avaliacao;
    LocalDateTime dataCriacao;
    LocalDateTime dataExpiracao;
    List<AvaliacaoItem> itensAvaliados = new ArrayList<>();

    public void criar(Long pedidoId, long clienteId){
        this.id = pedidoId;
        this.clienteId = clienteId;
        this.dataCriacao = LocalDateTime.now();
        this.dataExpiracao = LocalDateTime.now().minusDays(7);
    }

    public void mudarStatusAvaliacao(StatusAvaliacao status){
        if(status == null) {
            throw new StatusAvaliacaoNaoPodeSerVazioExcecao("O status da avaliacao não pode ser vazio");
        }
        this.status = status;
    }

    //um item não deve ser obrigado a ser avaliado, entao aceita null
    private void adicionarItemAvaliado(AvaliacaoItem itemAvaliado){
    if(itemAvaliado == null) {
        return;
    }
        itensAvaliados.add(itemAvaliado);
    }

    private void concluirAvaliacao(){
        this.status = StatusAvaliacao.CONCLUIDA;
    }

}
