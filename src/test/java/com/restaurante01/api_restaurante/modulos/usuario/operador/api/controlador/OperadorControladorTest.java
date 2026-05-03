package com.restaurante01.api_restaurante.modulos.usuario.operador.api.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.entrada.CadastrarOperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso.AtualizarOperadorCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso.CadastrarOperadorCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso.DeletarOperadorCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.operador.aplicacao.casodeuso.ListarTodosOperadoresCasoDeUso;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante01.api_restaurante.infraestrutura.security.springsecurity.SecurityConfigurations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OperadorControlador.class)
@Import(SecurityConfigurations.class)
class OperadorControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean CadastrarOperadorCasoDeUso cadastrarOperador;
    @MockitoBean ListarTodosOperadoresCasoDeUso listarOperadores;
    @MockitoBean AtualizarOperadorCasoDeUso atualizarOperador;
    @MockitoBean DeletarOperadorCasoDeUso deletarOperador;

    // PasswordEncoder vem do @Bean em SecurityConfigurations — não precisa de @MockitoBean.

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // /operador/cadastro → PUBLIC
    // /operador/obter-todos → ADMIN2 (SecurityConfigurations)
    // /operador/{id} (PUT) → ADMIN3 (SecurityConfigurations)
    // /operador/deletar/{id} → ADMIN3 (SecurityConfigurations)

    private CadastrarOperadorDTO dtoOperadorValido() {
        return new CadastrarOperadorDTO(
            "Bruno de Fraga",
            "12345678901",
            "bruno@empresa.com",
            "(51) 99999-9999",
            "brunodev01",
            "Senha@123",
            1234L
        );
    }

    @Test
    @DisplayName("Deve cadastrar operador com dados válidos e retornar 201 — rota pública")
    void deveCadastrarOperador() throws Exception {
        var operadorCriado = Instancio.create(OperadorDTO.class);
        when(cadastrarOperador.executar(any())).thenReturn(operadorCriado);

        mockMvc.perform(post("/operador/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoOperadorValido())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("Recurso criado"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando o nome estiver em branco no cadastro de operador")
    void deveRetornar400QuandoNomeEmBrancoNoCadastro() throws Exception {
        var dtoInvalido = new CadastrarOperadorDTO("", "12345678901", "bruno@empresa.com",
            "(51) 99999-9999", "brunodev01", "Senha@123", 1234L);

        mockMvc.perform(post("/operador/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN2")
    @DisplayName("Deve listar todos os operadores paginados quando autenticado como ADMIN2")
    void deveListarTodosOsOperadores() throws Exception {
        var pagina = new PageImpl<>(Instancio.ofList(OperadorDTO.class).size(2).create());
        when(listarOperadores.executar(any())).thenReturn(pagina);

        mockMvc.perform(get("/operador/obter-todos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso disponível"));
    }

    @Test
    @DisplayName("Deve retornar 403 ao tentar listar operadores sem autenticação")
    void deveRetornar403AoListarOperadoresSemAutenticacao() throws Exception {
        mockMvc.perform(get("/operador/obter-todos"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve atualizar dados de um operador quando autenticado como ADMIN3")
    void deveAtualizarOperador() throws Exception {
        var operadorAtualizado = Instancio.create(OperadorDTO.class);
        when(atualizarOperador.executar(anyLong(), any())).thenReturn(operadorAtualizado);

        mockMvc.perform(put("/operador/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operadorAtualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso atualizado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve deletar um operador pelo ID quando autenticado como ADMIN3")
    void deveDeletarOperador() throws Exception {
        doNothing().when(deletarOperador).executar(anyLong());

        mockMvc.perform(delete("/operador/deletar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso deletado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve retornar 403 quando ADMIN1 tentar deletar um operador — requer ADMIN3")
    void deveRetornar403QuandoAdmin1TentarDeletarOperador() throws Exception {
        mockMvc.perform(delete("/operador/deletar/1"))
            .andExpect(status().isForbidden());
    }
}
