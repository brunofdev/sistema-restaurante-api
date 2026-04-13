package com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api;

import java.time.LocalDateTime;

public class ApiError {
    private String error;
    private String message;
    private LocalDateTime timestamp;



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
