package com.restaurante01.api_restaurante.modulos.avaliacao.api.controlador;

import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.infraestrutura.security.springsecurity.SecurityConfigurations;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.ConsultaAvaliacoesConcluidasCasoDeUso;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.ConsultaAvaliacoesDisponiveisCasoDeUso;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.ConsultaAvaliacoesPendentesCasoDeUso;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.ConsultaTodasAvaliacoesCasoDeUso;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AvaliacaoOperadorControlador.class)
@Import(SecurityConfigurations.class)
class AvaliacaoOperadorControladorTest {

    @Autowired MockMvc mockMvc;

    @MockitoBean ConsultaTodasAvaliacoesCasoDeUso consultaTodasAvaliacoes;
    @MockitoBean ConsultaAvaliacoesPendentesCasoDeUso consultaAvaliacoesPendentes;
    @MockitoBean ConsultaAvaliacoesConcluidasCasoDeUso consultaAvaliacoesConcluidas;
    @MockitoBean ConsultaAvaliacoesDisponiveisCasoDeUso consultaAvaliacoesDisponiveis;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    private Authentication authAdmin() {
        List<SimpleGrantedAuthority> roles = List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN1"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
        return new UsernamePasswordAuthenticationToken("admin", null, roles);
    }

    // ==========================================
    // GET /avaliacao/operador/todas
    // ==========================================

    @Test
    @DisplayName("Deve retornar 200 com lista de todas as avaliações para admin autenticado")
    void deveListarTodasComSucesso() throws Exception {
        var avaliacoes = Instancio.ofList(AvaliacaoDTO.class).size(3).create();
        when(consultaTodasAvaliacoes.executar()).thenReturn(avaliacoes);

        mockMvc.perform(get("/avaliacao/operador/todas")
                        .with(authentication(authAdmin())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recurso obtido"))
                .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @DisplayName("Deve retornar 403 ao acessar /todas sem autenticação")
    void deveRetornar403AoListarTodasSemAutenticacao() throws Exception {
        mockMvc.perform(get("/avaliacao/operador/todas"))
                .andExpect(status().isForbidden());
    }

    // ==========================================
    // GET /avaliacao/operador/pendentes
    // ==========================================

    @Test
    @DisplayName("Deve retornar 200 com lista de avaliações pendentes para admin autenticado")
    void deveListarPendentesComSucesso() throws Exception {
        var avaliacoes = Instancio.ofList(AvaliacaoDTO.class).size(2).create();
        when(consultaAvaliacoesPendentes.executar()).thenReturn(avaliacoes);

        mockMvc.perform(get("/avaliacao/operador/pendentes")
                        .with(authentication(authAdmin())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recurso obtido"))
                .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @DisplayName("Deve retornar 403 ao acessar /pendentes sem autenticação")
    void deveRetornar403AoListarPendentesSemAutenticacao() throws Exception {
        mockMvc.perform(get("/avaliacao/operador/pendentes"))
                .andExpect(status().isForbidden());
    }

    // ==========================================
    // GET /avaliacao/operador/concluidas
    // ==========================================

    @Test
    @DisplayName("Deve retornar 200 com lista de avaliações concluídas para admin autenticado")
    void deveListarConcluidasComSucesso() throws Exception {
        var avaliacoes = Instancio.ofList(AvaliacaoDTO.class).size(2).create();
        when(consultaAvaliacoesConcluidas.executar()).thenReturn(avaliacoes);

        mockMvc.perform(get("/avaliacao/operador/concluidas")
                        .with(authentication(authAdmin())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recurso obtido"))
                .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @DisplayName("Deve retornar 403 ao acessar /concluidas sem autenticação")
    void deveRetornar403AoListarConcluidasSemAutenticacao() throws Exception {
        mockMvc.perform(get("/avaliacao/operador/concluidas"))
                .andExpect(status().isForbidden());
    }

    // ==========================================
    // GET /avaliacao/operador/disponiveis
    // ==========================================

    @Test
    @DisplayName("Deve retornar 200 com lista de avaliações disponíveis para admin autenticado")
    void deveListarDisponiveisComSucesso() throws Exception {
        var avaliacoes = Instancio.ofList(AvaliacaoDTO.class).size(2).create();
        when(consultaAvaliacoesDisponiveis.executar()).thenReturn(avaliacoes);

        mockMvc.perform(get("/avaliacao/operador/disponiveis")
                        .with(authentication(authAdmin())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recurso obtido"))
                .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @DisplayName("Deve retornar 403 ao acessar /disponiveis sem autenticação")
    void deveRetornar403AoListarDisponiveisSemAutenticacao() throws Exception {
        mockMvc.perform(get("/avaliacao/operador/disponiveis"))
                .andExpect(status().isForbidden());
    }
}
