package com.restaurante01.api_restaurante.cardapio.exceptions;

import com.restaurante01.api_restaurante.core.utils.ApiError;
import com.restaurante01.api_restaurante.core.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CardapioHandlerException {

    private ApiError buildApiError(HttpStatus status, String message) {
        ApiError apiError = new ApiError();
        apiError.setStatus(status.value());
        apiError.setError(status.getReasonPhrase());
        apiError.setMessage(message);
        apiError.setTimestamp(LocalDateTime.now());
        return apiError;
    }

    @ExceptionHandler(CardapioNomeInvalidoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleCardapioNomeInvalido(CardapioNomeInvalidoException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro ao cadastrar cardápio", apiError));
    }

    @ExceptionHandler(CardapioDataIniMaiorQueDataFimException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleCardapioDataIniMaiorQueDataFim(CardapioDataIniMaiorQueDataFimException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro nas datas do cardápio", apiError));
    }

    @ExceptionHandler(CardapioMesmoNomeExcepetion.class)
    public ResponseEntity<ApiResponse<ApiError>> handleCardapioMesmoNome(CardapioMesmoNomeExcepetion ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro ao cadastrar cardápio", apiError));
    }

    @ExceptionHandler(CardapioNaoEncontradoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleCardapioNaoEncontrado(CardapioNaoEncontradoException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Recurso a ser atualizado não existe no sistema", apiError));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse(ex.getMessage());

        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, mensagem);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro de validação nos dados do cardápio", apiError));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, "Formato do JSON inválido ou campos ausentes.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro na requisição", apiError));
    }


}
