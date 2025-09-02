package com.restaurante01.api_restaurante.produto.exceptions;
import com.restaurante01.api_restaurante.cardapio.exceptions.CardapioNomeInvalidoException;
import com.restaurante01.api_restaurante.produto.controller.ProdutoController;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(assignableTypes = ProdutoController.class)
public class ProdutoExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseError(HttpMessageNotReadableException ex) {
        String mensagemAmigavel =
                " ::::::::::Dados enviados de maneira errada::::::::::\n " +
                ":::Json deve ser neste formato para utilizar a api::::" +
                "{\n" +
                "    \"nome\": \"Café Premium master\", STRING\n" +
                "    \"preco\": 79.14,  DOUBLE\n" +
                "    \"descricao\": \"Café especial torrado e moído\", STRING\n" +
                "    \"quantidadeAtual\": 10, LONG\n" +
                "    \"disponibilidade\": dwdqwd  BOOLEAN\n" +
                "    }\n";
        return new ResponseEntity<>(mensagemAmigavel, HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> ConstraintViolationExceptionHandlerException(ConstraintViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<String> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
