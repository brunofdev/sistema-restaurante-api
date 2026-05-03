package com.restaurante01.api_restaurante.modulos.cardapio.api.controlador.privado;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.CriarCardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.AtualizarUmCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.CriarCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.DeletarUmCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.ObterTodosCardapiosCasoDeUso;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante01.api_restaurante.infraestrutura.security.springsecurity.SecurityConfigurations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardapioControlador.class)
@Import(SecurityConfigurations.class)
class CardapioControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean CriarCardapioCasoDeUso criarCardapio;
    @MockitoBean ObterTodosCardapiosCasoDeUso obterTodosCardapios;
    @MockitoBean AtualizarUmCardapioCasoDeUso atualizarCardapio;
    @MockitoBean DeletarUmCardapioCasoDeUso deletarCardapio;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // Todos os endpoints de /cardapio/** caem na regra geral: anyRequest().hasRole("ADMIN3")
    // pois não foram registrados explicitamente no SecurityConfigurations.

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve listar todos os cardápios quando autenticado como ADMIN3")
    void deveListarTodosOsCardapios() throws Exception {
        var listaCardapios = Instancio.ofList(CardapioDTO.class).size(2).create();
        when(obterTodosCardapios.executar()).thenReturn(listaCardapios);

        mockMvc.perform(get("/cardapio/todos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso encontrado"))
            .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @DisplayName("Deve retornar 403 quando tentar listar cardápios sem autenticação")
    void deveRetornar403SemAutenticacao() throws Exception {
        mockMvc.perform(get("/cardapio/todos"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve criar um cardápio com dados válidos e retornar 200")
    void deveCriarCardapio() throws Exception {
        var dto = new CriarCardapioDTO(
            "Cardápio de Verão",
            "Pratos leves para o verão",
            true,
            LocalDate.now(),
            LocalDate.now().plusMonths(3)
        );
        var cardapioCriado = Instancio.create(CardapioDTO.class);
        when(criarCardapio.executar(any())).thenReturn(cardapioCriado);

        mockMvc.perform(post("/cardapio/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso criado"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve retornar 400 quando criar cardápio com nome em branco")
    void deveRetornar400QuandoNomeEmBranco() throws Exception {
        // Nome vazio dispara @NotBlank
        var dto = new CriarCardapioDTO("", "Descrição válida", true, LocalDate.now(), LocalDate.now().plusDays(30));

        mockMvc.perform(post("/cardapio/criar")
                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve atualizar um cardápio com dados válidos e retornar 200")
    void deveAtualizarCardapio() throws Exception {
        var dto = new CardapioDTO(1L, "Cardápio Atualizado", "Nova descrição", true, LocalDate.now(), LocalDate.now().plusMonths(1));
        var cardapioAtualizado = Instancio.create(CardapioDTO.class);
        when(atualizarCardapio.executar(any())).thenReturn(cardapioAtualizado);

        mockMvc.perform(put("/cardapio/atualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso atualizado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve deletar um cardápio pelo ID e retornar 200")
    void deveDeletarCardapio() throws Exception {
        doNothing().when(deletarCardapio).executar(any());

        mockMvc.perform(delete("/cardapio/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso removido"));
    }
}
