package com.restaurante01.api_restaurante.config;
import com.restaurante01.api_restaurante.controllers.ProdutoController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = ProdutoController.class)
public class ProdutoExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseError(HttpMessageNotReadableException ex) {
        String mensagemAmigavel =
                " ::::::::::Dados Invalidos de maneira errada::::::::::\n " +
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
}
