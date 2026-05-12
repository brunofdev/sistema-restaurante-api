package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.ClassificacaoAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.TentativaNotificacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.*;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long pedidoId;
    @NotNull
    private Long clienteId;
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "nota_valor"))
    private NotaAvaliacao nota;
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "comentario_valor"))
    private ComentarioAvaliacao comentarioAvaliacao;
    @Enumerated(EnumType.STRING)
    private StatusAvaliacao status;
    @Enumerated(EnumType.STRING)
    private ClassificacaoAvaliacao avaliacao;
    @Enumerated(EnumType.STRING)
    private TentativaNotificacao numeroNotificacaoCliente;
    @NotNull
    LocalDateTime dataCriacao;
    @NotNull
    LocalDateTime dataExpiracao;
    @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AvaliacaoItem> itensAvaliados = new ArrayList<>();

    public static Avaliacao criar(Long pedidoId, Long clienteId, List<AvaliacaoItem> itensParaAvaliacao){
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setPedidoId(pedidoId);
        avaliacao.setClienteId(clienteId);
        avaliacao.adicionarListaDeItensParaAvaliacao(itensParaAvaliacao);
        avaliacao.status = StatusAvaliacao.PENDENTE;
        avaliacao.dataCriacao = LocalDateTime.now(); //após uma hora do pedido entregue a primeira tentativa de notificacao deve ser entregue
        avaliacao.dataExpiracao = LocalDateTime.now().plusDays(7);
        return avaliacao;
    }

    private void setPedidoId(Long pedidoId){
        if(pedidoId == null) {
            throw new IdPedidoAvaliacaoVazioExcecao("Id pedido não pode ser Vazio");
        }
        this.pedidoId = pedidoId;
    }
    private void setClienteId(Long clienteId){
        if(clienteId == null) {
            throw new IdClienteVazioExcecao("Id do cliente não pode ser vazio");
        }
        this.clienteId = clienteId;
    }

    protected void mudarStatusAvaliacao(StatusAvaliacao status){
        if (!this.status.podeTransicionarPara(status)) {
            throw new StatusAvaliacaoInvalidoExcecao(
                    "Transição inválida: " + this.status + " -> " + status
            );
        }
        this.status = status;
    }

    public void foiEnviadaAoCliente(){
        if(this.numeroNotificacaoCliente == null) {
            this.numeroNotificacaoCliente = TentativaNotificacao.PRIMEIRA_TENTATIVA;
            return;
        }
        this.numeroNotificacaoCliente = numeroNotificacaoCliente.proxima();

    }

    //um item não deve ser obrigado a ser avaliado, entao aceita null
    private void adicionarListaDeItensParaAvaliacao(List<AvaliacaoItem> itensParaAvaliar){
        if(itensParaAvaliar.isEmpty()) {
            throw new ItemAvaliadoVazioExcecao("É obrigatório informar os produtos para serem avaliados");
        }
        for (AvaliacaoItem item : itensParaAvaliar) {
            item.setAvaliacao(this);
            this.itensAvaliados.add(item);
        }
    }
    protected void expirarAvaliacao(){
        if(LocalDateTime.now().isAfter(this.dataExpiracao)) {
            this.status = StatusAvaliacao.EXPIRADA;
            return;
        }
        throw new AvaliacaoNaoExpiradaExcecao("A data de expiração ainda não chegou para esta avaliação");

    }
    protected void concluirAvaliacao(NotaAvaliacao nota, ComentarioAvaliacao comentario){
        validarSePodeTransicionarPara();
        vincularAvaliacao(nota, comentario);
        classificarAvaliacao(nota);
        this.status = StatusAvaliacao.CONCLUIDA;
    }

    private void validarSePodeTransicionarPara(){
        if(!this.status.podeTransicionarPara(StatusAvaliacao.CONCLUIDA)){
            throw new StatusAvaliacaoInvalidoExcecao("Avaliação não pode ser concluída no status atual.");
        }
    }
    private void vincularAvaliacao(NotaAvaliacao nota, ComentarioAvaliacao comentarioAvaliacao){
        if(nota == null && comentarioAvaliacao != null){
            throw new AvaliacaoInvalidaExcecao("Avaliação obrigatoriamente deve possuir uma Nota.");
        }
        //Voto em branco: aceita e não faz nada
        if(nota == null){
            this.nota = null;
            this.comentarioAvaliacao = null;
            return;
        }
        //Cenário de sucesso: preenche o que veio (usando ternário para o default)
        this.nota = nota;
        this.comentarioAvaliacao = (comentarioAvaliacao != null) ? comentarioAvaliacao : new ComentarioAvaliacao("Avaliação feita sem comentário");
    }
    private void classificarAvaliacao(NotaAvaliacao nota){
        if(nota == null) {
            this.avaliacao = ClassificacaoAvaliacao.NAO_AVALIADO;
            return;
        }
        this.avaliacao = ClassificacaoAvaliacao.derivarDaNota(nota);
    }
}
