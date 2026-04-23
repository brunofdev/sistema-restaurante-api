package com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiError {
    private String error;
    private String message;
    private LocalDateTime timestamp;


}
