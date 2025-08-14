package com.restaurante01.api_restaurante.log.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "logs_sistema")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String error;

    @NotNull
    private String mensagem;

    @NotNull
    private String linha;

    @NotNull
    private String nomeClasse;

    @NotNull
    private LocalDate data;

    @NotNull
    private LocalTime hora;

    public LogEntity() {}

    public LogEntity(String error, String mensagem, String linha, String nomeClasse) {
        this.error = error;
        this.mensagem = mensagem;
        this.linha = linha;
        this.nomeClasse = nomeClasse;
    }

    @PrePersist
    public void prePersist() {
        this.data = LocalDate.now();
        this.hora = LocalTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String menssagem) {
        this.mensagem = menssagem;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
