package com.restaurante01.api_restaurante.compartilhado.dominio.entidade;

import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.StatusOutbox;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "outbox_eventos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"agregado_tipo",
                "agregado_id"})
)
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OutboxEvento {

    private static final int MAX_TENTATIVAS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "agregado_tipo", nullable = false, length = 50)
    private Agregado agregadoTipo;

    @Column(name = "agregado_id", nullable = false)
    private Long agregadoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 100)
    private TipoEvento tipo;

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusOutbox status;

    @Column(name = "tentativas", nullable = false)
    private int tentativas;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "processado_em")
    private LocalDateTime processadoEm;

    public static OutboxEvento criar(Agregado agregadoTipo, Long agregadoId, TipoEvento tipo, String payload) {
        OutboxEvento evento = new OutboxEvento();
        evento.agregadoTipo = agregadoTipo;
        evento.agregadoId = agregadoId;
        evento.tipo = tipo;
        evento.payload = payload;
        evento.status = StatusOutbox.PENDENTE;
        evento.tentativas = 0;
        evento.criadoEm = LocalDateTime.now();
        return evento;
    }

    public void processar() {
        if (this.status == StatusOutbox.PROCESSADO) {
            throw new RegraDeNegocioExcecao("Evento já foi processado: " + this.id);
        }
        this.status = StatusOutbox.PROCESSADO;
        this.processadoEm = LocalDateTime.now();
    }

    public void registrarFalha() {
        if (this.status == StatusOutbox.PROCESSADO) {
            throw new RegraDeNegocioExcecao("Não é possível registrar falha em evento já processado: " + this.id);
        }
        this.tentativas++;
        if (this.tentativas >= MAX_TENTATIVAS) {
            this.status = StatusOutbox.ERRO;
        }
    }

    public boolean pendente() {
        return this.status == StatusOutbox.PENDENTE;
    }

    public boolean esgotouTentativas() {
        return this.tentativas >= MAX_TENTATIVAS;
    }
}
