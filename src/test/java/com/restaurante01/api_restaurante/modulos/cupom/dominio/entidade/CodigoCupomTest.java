package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;


import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CodigoCupomInvalidoExcecao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CodigoCupomTest {

    @Test
    @DisplayName("Deve criar o código quando o formato for válido")
    void deveCriarCodigoComSucesso() {
        CodigoCupom codigo = new CodigoCupom("PROMO10");
        assertEquals("PROMO10", codigo.getValor());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("Deve lançar exceção quando código for nulo ou em branco")
    void deveLancarExcecaoQuandoNuloOuVazio(String valorInvalido) {
        assertThrows(CodigoCupomInvalidoExcecao.class, () -> new CodigoCupom(valorInvalido));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tamanho for menor que 5 caracteres")
    void deveLancarExcecaoQuandoTamanhoMenorQueMinimo() {
        assertThrows(CodigoCupomInvalidoExcecao.class, () -> new CodigoCupom("A12"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tamanho for maior que 15 caracteres")
    void deveLancarExcecaoQuandoTamanhoMaiorQueMaximo() {
        assertThrows(CodigoCupomInvalidoExcecao.class, () -> new CodigoCupom("CODIGOMUITOGRANDEXXX"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"promo10", "PROMO 10", "PROMO@10", "PROMO-10"})
    @DisplayName("Deve lançar exceção quando possuir caracteres minúsculos, espaços ou especiais")
    void deveLancarExcecaoQuandoPossuiCaracteresInvalidos(String valorInvalido) {
        assertThrows(CodigoCupomInvalidoExcecao.class, () -> new CodigoCupom(valorInvalido));
    }
}