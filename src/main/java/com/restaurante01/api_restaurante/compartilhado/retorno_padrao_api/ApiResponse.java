package com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // ignora campos nulos no JSON
public class ApiResponse<T> {
    private String message;
    private T dados;
    private ApiError erro;

    public ApiResponse() {
    }

    public ApiResponse(String message, T dados, ApiError erro) {
        this.message = message;
        this.dados = dados;
        this.erro = erro;
    }

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<>(message, data, null);
    }
    public static <T> ApiResponse<T> error(String message, ApiError erro){
        return new ApiResponse<>("Erro ao utilizar o recurso", null, erro);
    }

}
