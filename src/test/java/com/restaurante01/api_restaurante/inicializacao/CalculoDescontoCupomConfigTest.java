package com.restaurante01.api_restaurante.inicializacao;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.CalculoDescontoCupom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CalculoDescontoCupomConfigTest {

    @Autowired
    Map<String, CalculoDescontoCupom> calculoDescontoCupom;

    @Test
    void deveTerEstrategiaParaCadaTipoDesconto() {
        for (TipoDesconto tipo : TipoDesconto.values()) {
            assertThat(calculoDescontoCupom.get(tipo.name()))
                    .as("Estratégia não encontrada para: " + tipo)
                    .isNotNull();
        }
    }

    @Test
    void deveTerEnumParaCadaEstrategiaMapeada() {
        Set<String> nomesDoEnum = Arrays.stream(TipoDesconto.values())
                .map(TipoDesconto::name)
                .collect(Collectors.toSet());

        for (String chave : calculoDescontoCupom.keySet()) {
            assertThat(nomesDoEnum)
                    .as("Chave no mapa sem correspondência no enum: " + chave)
                    .contains(chave);
        }
    }
}