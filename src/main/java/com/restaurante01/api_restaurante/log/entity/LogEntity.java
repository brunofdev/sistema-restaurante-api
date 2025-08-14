package com.restaurante01.api_restaurante.log.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "logs_sistema")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String error;
    @NotNull
    private String menssagem;
    @NotNull
    private String linha;
    @NotNull
    private String nomeClasse;
    @NotNull
    private LocalDate data;
    @NotNull
    private LocalTime hora;

    public LogEntity (){
    }

    public LogEntity(long id, String error, String menssagem, String linha, String nomeClasse, LocalDate data, LocalTime hora) {
        this.id = id;
        this.error = error;
        this.menssagem = menssagem;
        this.linha = linha;
        this.nomeClasse = nomeClasse;
        this.data = data;
        this.hora = hora;
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

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
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
