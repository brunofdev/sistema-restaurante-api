package com.restaurante01.api_restaurante.produto.exceptions;
import com.restaurante01.api_restaurante.core.utils.ApiError;
import com.restaurante01.api_restaurante.core.utils.ApiResponse;
import com.restaurante01.api_restaurante.produto.controller.ProdutoController;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(assignableTypes = ProdutoController.class)
public class ProdutoExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleConstraintViolation(ConstraintViolationException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setError("Bad Request");
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro ao utilizar o recurso", apiError));
    }

    @ExceptionHandler(ProdutoMesmoNomeExistenteException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleProdutoMesmoNomeExistente(ProdutoMesmoNomeExistenteException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setError("Bad Request");
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro ao utilizar o recurso", apiError));
    }

    @ExceptionHandler(ProdutoNomeInvalidoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleProdutoNomeInvalido(ProdutoNomeInvalidoException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setError("Bad Request");
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro ao utilizar o recurso", apiError));
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex){
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND.value());
        apiError.setError("Not Found");
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Erro ao buscar recurso", apiError));
    }



}

