package com.restaurante01.api_restaurante.modulos.cardapio.api.controlador.publico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacoesDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.ListarAssociacaoPorCardapioIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.ListarTodasAssociacoesCasoDeUso;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante01.api_restaurante.infraestrutura.security.springsecurity.SecurityConfigurations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardapioProdutoPublicoControlador.class)
@Import(SecurityConfigurations.class)
class CardapioProdutoPublicoControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean ListarTodasAssociacoesCasoDeUso listarTodasAssociacoes;
    @MockitoBean ListarAssociacaoPorCardapioIdCasoDeUso listarPorCardapioId;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // Rotas /cardapio-produto/publico/** estão listadas em PUBLIC_ENDPOINTS —
    // qualquer pessoa pode acessar, sem token.

    @Test
    @DisplayName("Deve listar todas as associações sem autenticação e retornar 200")
    void deveListarTodasAsAssociacoesSemAutenticacao() throws Exception {
        var listaEsperada = Instancio.ofList(AssociacoesDTO.class).size(3).create();
        when(listarTodasAssociacoes.executar()).thenReturn(listaEsperada);

        mockMvc.perform(get("/cardapio-produto/publico/todas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso disponibilizado"))
            .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @DisplayName("Deve retornar associação de um cardápio pelo ID sem autenticação")
    void deveRetornarAssociacaoPorCardapioId() throws Exception {
        var associacaoEsperada = Instancio.create(AssociacaoDTO.class);
        when(listarPorCardapioId.executar(anyLong())).thenReturn(associacaoEsperada);

        mockMvc.perform(get("/cardapio-produto/publico/cardapio/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso encontrado"))
            .andExpect(jsonPath("$.dados").exists());
    }
}
