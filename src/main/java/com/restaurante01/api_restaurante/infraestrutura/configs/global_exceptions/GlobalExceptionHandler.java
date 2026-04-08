package com.restaurante01.api_restaurante.infraestrutura.configs.global_exceptions;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.*;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.*;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoExistenteCardapioProdutoException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiError;
import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.compartilhado.utils.validadorcpf.CpfComTamanhoInvalidoException;
import com.restaurante01.api_restaurante.compartilhado.utils.validadorcpf.NumeroCpfInvalidoException;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.StatusPedidoInvalidoException;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.StatusPedidoNaoPodeMaisSerAlteradoException;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.*;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Padronização do corpo da resposta de erro
    private ResponseEntity<ApiResponse<Object>> buildError(HttpStatus status, String error, String message, String responseMessage) {
        ApiError apiError = new ApiError();
        apiError.setStatus(status.value());
        apiError.setError(error);
        apiError.setMessage(message);
        apiError.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(status).body(ApiResponse.error(responseMessage, apiError));
    }

    // --- Exceções de Segurança ---

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        return buildError(
                HttpStatus.FORBIDDEN,
                "Access Denied",
                ex.getMessage(),
                "Você não tem permissão para acessar ou modificar este recurso."
        );
    }

    // --- Exceções de Validação e Spring ---

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação.");

        return buildError(HttpStatus.BAD_REQUEST, "Validation Failed", mensagem, "Dados de entrada inválidos.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraint(ConstraintViolationException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "Constraint Violation", ex.getMessage(), "Erro de validação dos dados enviados.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleJSON(HttpMessageNotReadableException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "Malformed JSON", "Formato do JSON inválido ou campos ausentes.", "Erro na requisição.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleIntegrity(DataIntegrityViolationException ex) {
        String detalhe = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : "Violação de integridade no banco.";
        return buildError(HttpStatus.CONFLICT, "Data Integrity Violation", detalhe, "Conflito ao processar os dados.");
    }

    // --- Exceções Customizadas de Regra de Negócio ---

    @ExceptionHandler({
            InvalidCredentialsException.class,
            UserDontHaveEmailRegistered.class,
            EmailAlreadyExistsException.class,
            UsernameAlreadyExistsException.class,
            UserNotFoundException.class,
            UserDontFoundException.class,
            CpfComTamanhoInvalidoException.class,
            NumeroCpfInvalidoException.class,
            CpfAlreadyExistsException.class,
            ProdutoNaoEncontradoException.class,
            ProdutoNomeInvalidoException.class,
            ProdutoMesmoNomeExistenteException.class,
            ProdutoDescricaoInvalidaExcpetion.class,
            ProdutoIdNegativoException.class,
            ProdutoIdVazioException.class,
            PrecoProdutoNegativoException.class,
            ProdutoQntdNegativa.class,
            CardapioNomeInvalidoException.class,
            CardapioDataIniMaiorQueDataFimException.class,
            CardapioMesmoNomeExcepetion.class,
            CardapioNaoEncontradoException.class,
            CardapioIdNegativoException.class,
            CardapioIdVazioException.class,
            AssociacaoExistenteCardapioProdutoException.class,
            AssociacaoNaoExisteException.class,
            StatusPedidoNaoPodeMaisSerAlteradoException.class,
            StatusPedidoInvalidoException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleCustom(RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex instanceof ProdutoNaoEncontradoException ||
                ex instanceof CardapioNaoEncontradoException ||
                ex instanceof UserNotFoundException ||
                ex instanceof UserDontFoundException ||
                ex instanceof AssociacaoNaoExisteException) {
            status = HttpStatus.NOT_FOUND;
        }

        if (ex instanceof InvalidCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
        }

        return buildError(status, status.getReasonPhrase(), ex.getMessage(), "Erro ao processar a requisição.");
    }

    // --- Exceção Genérica (Failsafe) ---

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        logger.error("Erro inesperado:", ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Ocorreu um erro inesperado no servidor.", "Falha interna no servidor.");
    }
}