package com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.ClienteLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.CredenciaisDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto.OperadorLoginResponseDTO;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutenticacao;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante01.api_restaurante.infraestrutura.security.springsecurity.SecurityConfigurations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@Import(SecurityConfigurations.class)
class AuthenticationControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean ServicoAutenticacao servicoAutenticacao;

    // O SecurityFilter depende de JwtProvider e ServicoAutorizacao.
    // Precisam ser mockados em TODOS os testes de controller para o contexto Spring subir.
    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    @Test
    @DisplayName("Deve autenticar cliente com credenciais válidas e retornar 200")
    void deveAutenticarClienteComCredenciaisValidas() throws Exception {
        var credenciais = new CredenciaisDTO("12345678901", "senha123");
        var respostaEsperada = Instancio.create(ClienteLoginResponseDTO.class);

        when(servicoAutenticacao.loginCliente(any())).thenReturn(respostaEsperada);

        mockMvc.perform(post("/api/auth/cliente-login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credenciais)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Usuário autenticado com sucesso"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando o CPF estiver em branco no login de cliente")
    void deveRetornar400QuandoCpfEmBrancoNoLoginCliente() throws Exception {
        // CPF vazio dispara @NotBlank → validação falha antes de chamar o serviço
        var credenciais = new CredenciaisDTO("", "senha123");

        mockMvc.perform(post("/api/auth/cliente-login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credenciais)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve autenticar operador com credenciais válidas e retornar 200")
    void deveAutenticarOperadorComCredenciaisValidas() throws Exception {
        var credenciais = new CredenciaisDTO("12345678901", "senha123");
        var respostaEsperada = Instancio.create(OperadorLoginResponseDTO.class);

        when(servicoAutenticacao.loginOperador(any())).thenReturn(respostaEsperada);

        mockMvc.perform(post("/api/auth/operador-login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credenciais)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Usuário autenticado com sucesso"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando a senha estiver em branco no login de operador")
    void deveRetornar400QuandoSenhaEmBrancoNoLoginOperador() throws Exception {
        // Senha vazia dispara @NotBlank
        var credenciais = new CredenciaisDTO("12345678901", "");

        mockMvc.perform(post("/api/auth/operador-login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(credenciais)))
            .andExpect(status().isBadRequest());
    }
}
