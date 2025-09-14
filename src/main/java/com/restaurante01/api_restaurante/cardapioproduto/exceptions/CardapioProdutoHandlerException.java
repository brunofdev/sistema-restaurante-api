package com.restaurante01.api_restaurante.cardapioproduto.exceptions;

import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioIdNegativoException;
import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioIdVazioException;
import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioNaoEncontradoException;
import com.restaurante01.api_restaurante.cardapioproduto.controller.CardapioProdutoController;
import com.restaurante01.api_restaurante.core.utils.ApiError;
import com.restaurante01.api_restaurante.core.utils.ApiResponse;
import com.restaurante01.api_restaurante.produto.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice(assignableTypes = CardapioProdutoController.class)
public class CardapioProdutoHandlerException {

    private ApiError buildApiError(HttpStatus status, String message) {
        ApiError apiError = new ApiError();
        apiError.setStatus(status.value());
        apiError.setError(status.getReasonPhrase());
        apiError.setMessage(message);
        apiError.setTimestamp(LocalDateTime.now());
        return apiError;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, "Formato do JSON inválido ou campos ausentes.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro na requisição", apiError));
    }

    @ExceptionHandler(AssociacaoExistenteCardapioProdutoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleAssociacaoExistente(AssociacaoExistenteCardapioProdutoException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Erro ao associar produto ao cardápio", apiError));
    }

    @ExceptionHandler(AssociacaoNaoExisteException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleAssociacaoNaoExiste(AssociacaoNaoExisteException ex) {
        ApiError apiError = buildApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Associação não encontrada", apiError));
    }

    @ExceptionHandler(CardapioIdNegativoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleCardapioIdNegativo(CardapioIdNegativoException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("ID do cardápio inválido", apiError));
    }

    @ExceptionHandler(CardapioIdVazioException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleCardapioIdVazio(CardapioIdVazioException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("ID do cardápio não informado", apiError));
    }

    @ExceptionHandler(CardapioNaoEncontradoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleCardapioNaoEncontrado(CardapioNaoEncontradoException ex) {
        ApiError apiError = buildApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Cardápio não encontrado", apiError));
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex) {
        ApiError apiError = buildApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Produto não encontrado", apiError));
    }

    @ExceptionHandler(ProdutoIdVazioException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleProdutoIdVazio(ProdutoIdVazioException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("ID do produto não informado", apiError));
    }

    @ExceptionHandler(ProdutoIdNegativoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleProdutoIdNegativo(ProdutoIdNegativoException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("ID do produto inválido", apiError));
    }

    @ExceptionHandler(PrecoProdutoNegativoException.class)
    public ResponseEntity<ApiResponse<ApiError>> handlePrecoProdutoNegativo(PrecoProdutoNegativoException ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Preço do produto inválido", apiError));
    }

    @ExceptionHandler(ProdutoQntdNegativa.class)
    public ResponseEntity<ApiResponse<ApiError>> handleProdutoQntdNegativa(ProdutoQntdNegativa ex) {
        ApiError apiError = buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Quantidade do produto inválida", apiError));
    }


}
