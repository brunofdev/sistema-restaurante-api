package com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api;

import com.fasterxml.jackson.annotation.JsonInclude;

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

    public ApiError getErro() {
        return erro;
    }

    public void setErro(ApiError erro) {
        this.erro = erro;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }
}
