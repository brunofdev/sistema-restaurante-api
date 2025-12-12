package com.restaurante01.api_restaurante.configs.global_exceptions;

import com.restaurante01.api_restaurante.cardapio.exceptions.*;
import com.restaurante01.api_restaurante.cardapioproduto.exceptions.AssociacaoExistenteCardapioProdutoException;
import com.restaurante01.api_restaurante.cardapioproduto.exceptions.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.core.utils.retorno_padrao_api.ApiError;
import com.restaurante01.api_restaurante.core.utils.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.core.utils.validadorcpf.CpfComTamanhoInvalidoException;
import com.restaurante01.api_restaurante.core.utils.validadorcpf.NumeroCpfInvalidoException;
import com.restaurante01.api_restaurante.pedido.exception.StatusPedidoInvalidoException;
import com.restaurante01.api_restaurante.pedido.exception.StatusPedidoNaoPodeMaisSerAlteradoException;
import com.restaurante01.api_restaurante.produto.exceptions.*;
import com.restaurante01.api_restaurante.usuarios.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // -------------------------------------------------------------------------
    // METODO PADRÃO PARA CRIAR RESPOSTAS DE ERRO
    // -------------------------------------------------------------------------
    private ResponseEntity<ApiResponse<Object>> buildError(
            HttpStatus status,
            String error,
            String message,
            String responseMessage) {

        ApiError apiError = new ApiError();
        apiError.setStatus(status.value());
        apiError.setError(error);
        apiError.setMessage(message);
        apiError.setTimestamp(LocalDateTime.now());

        ApiResponse<Object> response =
                ApiResponse.error(responseMessage, apiError);

        return ResponseEntity.status(status).body(response);
    }

    // -------------------------------------------------------------------------
    // EXCEÇÕES DO SPRING / VALIDAÇÃO
    // -------------------------------------------------------------------------

    //Erros de validação em DTOs (@RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação.");

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                mensagem,
                "Dados de entrada inválidos."
        );
    }

    //Erros de validação em parâmetros (@PathVariable, @RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraint(ConstraintViolationException ex) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "Constraint Violation",
                ex.getMessage(),
                "Erro de validação dos dados enviados."
        );
    }

    //JSON mal formatado / tipos inválidos no corpo
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleJSON(HttpMessageNotReadableException ex) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON",
                "Formato do JSON inválido ou campos ausentes.",
                "Erro na requisição."
        );
    }

    //Violação de UNIQUE, FK, NOT NULL, etc.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleIntegrity(DataIntegrityViolationException ex) {

        String detalhe = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : "Violação de integridade no banco.";

        return buildError(
                HttpStatus.CONFLICT,
                "Data Integrity Violation",
                detalhe,
                "Conflito ao processar os dados. Verifique valores únicos ou relacionamentos."
        );
    }

    // -------------------------------------------------------------------------
    // EXCEÇÕES CUSTOMIZADAS DA SUA APLICAÇÃO
    // -------------------------------------------------------------------------
    @ExceptionHandler({
            // AUTENTICAÇÃO
            InvalidCredentialsException.class,
            UserDontHaveEmailRegistered.class,
            EmailAlreadyExistsException.class,
            UsernameAlreadyExistsException.class,

            // USUÁRIO
            UserNotFoundException.class,
            UserDontFoundException.class,
            CpfComTamanhoInvalidoException.class,
            NumeroCpfInvalidoException.class,
            CpfAlreadyExistsException.class,

            // PRODUTO
            ProdutoNaoEncontradoException.class,
            ProdutoNomeInvalidoException.class,
            ProdutoMesmoNomeExistenteException.class,
            ProdutoDescricaoInvalidaExcpetion.class,
            ProdutoIdNegativoException.class,
            ProdutoIdVazioException.class,
            PrecoProdutoNegativoException.class,
            ProdutoQntdNegativa.class,

            // CARDÁPIO
            CardapioNomeInvalidoException.class,
            CardapioDataIniMaiorQueDataFimException.class,
            CardapioMesmoNomeExcepetion.class,
            CardapioNaoEncontradoException.class,
            CardapioIdNegativoException.class,
            CardapioIdVazioException.class,

            // CARDÁPIO PRODUTO
            AssociacaoExistenteCardapioProdutoException.class,
            AssociacaoNaoExisteException.class,

            //Pedido
            StatusPedidoNaoPodeMaisSerAlteradoException.class,
            StatusPedidoInvalidoException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleCustom(RuntimeException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Erros 404
        if (ex instanceof ProdutoNaoEncontradoException ||
                ex instanceof CardapioNaoEncontradoException ||
                ex instanceof UserNotFoundException ||
                ex instanceof UserDontFoundException ||
                ex instanceof AssociacaoNaoExisteException) {
            status = HttpStatus.NOT_FOUND;
        }

        // Erro 401
        if (ex instanceof InvalidCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
        }

        return buildError(
                status,
                status.getReasonPhrase(),
                ex.getMessage(),
                "Erro ao processar a requisição."
        );
    }

    // -------------------------------------------------------------------------
    // EXCEÇÃO GENÉRICA
    // -------------------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        logger.error("Erro inesperado:", ex);

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "Ocorreu um erro inesperado no servidor.",
                "Falha interna no servidor."
        );
    }
}
