package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.ClienteIdFidelidadeVazioExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.MotivoRegistroVazioExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.objeto_de_valor.PontuacaoAtual;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.objeto_de_valor.RegistroPontuacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "fidelidades")
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AttributeOverride(name = "pontuacaoAtual.valor", column = @Column(name = "pontuacao_atual"))
public class Fidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long clienteId;

    @Embedded
    private PontuacaoAtual pontuacaoAtual;

    @ElementCollection
    @CollectionTable(name = "fidelidade_historico", joinColumns = @JoinColumn(name = "fidelidade_id"))
    @OrderColumn(name = "posicao")
    List<RegistroPontuacao> historico = new ArrayList<>();

    public static Fidelidade criar(Long clienteId) {
        Fidelidade fidelidade = new Fidelidade();
        fidelidade.setClienteId(clienteId);
        fidelidade.pontuacaoAtual = PontuacaoAtual.zero();
        return fidelidade;
    }

    private void setClienteId(Long clienteId) {
        if (clienteId == null) {
            throw new ClienteIdFidelidadeVazioExcecao("O ID do cliente vinculado à fidelidade não pode ser nulo.");
        }
        this.clienteId = clienteId;
    }

    public void creditarPontos(Integer pontos, String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new MotivoRegistroVazioExcecao("O motivo da pontuação não pode ser vazio.");
        }
        Integer novoSaldo = this.pontuacaoAtual.valor() + pontos;
        this.historico.add(RegistroPontuacao.criar(pontos, motivo, novoSaldo));
        this.pontuacaoAtual = this.pontuacaoAtual.acrescentar(pontos);
    }

    public List<RegistroPontuacao> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}
