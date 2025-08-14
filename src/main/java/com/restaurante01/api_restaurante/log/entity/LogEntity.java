package com.restaurante01.api_restaurante.log.entity;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Repository
public class LogEntity {
    private long id;
    private String error;
    private String menssagem;
    private String linha;
    private String nomeClasse;
    private LocalDate data;
    private LocalTime hora;
}
