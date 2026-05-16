package com.restaurante01.api_restaurante.modulos.avaliacao.api.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.infraestrutura.security.springsecurity.SecurityConfigurations;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.AvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.ItemAvaliadoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada.ResponderAvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoPendenteClienteDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.BuscarAvaliacoesPendentesClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.ConcluirAvaliacaoAvaliadaCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AvaliacaoClienteControlador.class)
@Import(SecurityConfigurations.class)
class AvaliacaoClienteControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean BuscarAvaliacoesPendentesClienteCasoDeUso buscarPendentes;
    @MockitoBean ConcluirAvaliacaoAvaliadaCasoDeUso concluirAvaliacao;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    private Authentication authCliente() {
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(1L);
        List<SimpleGrantedAuthority> roles = List.of(
                new SimpleGrantedAuthority("ROLE_CLIENT"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
        when(cliente.getAuthorities()).thenAnswer(inv -> roles);
        return new UsernamePasswordAuthenticationToken(cliente, null, roles);
    }

    private ResponderAvaliacaoDTO dtoValido() {
        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(5, "Chegou quentinho!");
        ItemAvaliadoDTO itemDTO = new ItemAvaliadoDTO(1L, 1L, avaliacaoDTO);
        return new ResponderAvaliacaoDTO(1L, avaliacaoDTO, List.of(itemDTO));
    }

    // ==========================================
    // GET /avaliacao/cliente/pendentes
    // ==========================================

    @Test
    @DisplayName("Deve retornar 200 com lista de avaliações pendentes para cliente autenticado")
    void deveListarPendentesComSucesso() throws Exception {
        var pendentes = Instancio.ofList(AvaliacaoPendenteClienteDTO.class).size(2).create();
        when(buscarPendentes.executar(1L)).thenReturn(pendentes);

        mockMvc.perform(get("/avaliacao/cliente/pendentes")
                        .with(authentication(authCliente())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recurso encontrado"))
                .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @DisplayName("Deve retornar 403 ao listar pendentes sem autenticação")
    void deveRetornar403AoListarPendentesSemAutenticacao() throws Exception {
        mockMvc.perform(get("/avaliacao/cliente/pendentes"))
                .andExpect(status().isForbidden());
    }

    // ==========================================
    // POST /avaliacao/cliente/concluir
    // ==========================================

    @Test
    @DisplayName("Deve retornar 200 ao concluir avaliação com DTO completo e cliente autenticado")
    void deveConcluirAvaliacaoComSucesso() throws Exception {
        doNothing().when(concluirAvaliacao).executar(eq(1L), any());

        mockMvc.perform(post("/avaliacao/cliente/concluir")
                        .with(authentication(authCliente()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoValido())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Avaliação concluída com sucesso"));
    }

    @Test
    @DisplayName("Deve retornar 200 ao concluir com voto em branco (apenas idAvaliacao)")
    void deveConcluirComVotoEmBranco() throws Exception {
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, null, null);
        doNothing().when(concluirAvaliacao).executar(eq(1L), any());

        mockMvc.perform(post("/avaliacao/cliente/concluir")
                        .with(authentication(authCliente()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 403 ao tentar concluir sem autenticação")
    void deveRetornar403AoConcluirSemAutenticacao() throws Exception {
        mockMvc.perform(post("/avaliacao/cliente/concluir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoValido())))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve retornar 400 quando idAvaliacao for nulo")
    void deveRetornar400ComIdAvaliacaoNulo() throws Exception {
        ResponderAvaliacaoDTO dtoInvalido = new ResponderAvaliacaoDTO(null, null, null);

        mockMvc.perform(post("/avaliacao/cliente/concluir")
                        .with(authentication(authCliente()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando nota estiver fora do intervalo permitido (> 5)")
    void deveRetornar400ComNotaAcimaDoLimite() throws Exception {
        AvaliacaoDTO avaliacaoInvalida = new AvaliacaoDTO(10, null);
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, avaliacaoInvalida, null);

        mockMvc.perform(post("/avaliacao/cliente/concluir")
                        .with(authentication(authCliente()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando nota for inferior ao mínimo permitido (< 1)")
    void deveRetornar400ComNotaAbaixoDoMinimo() throws Exception {
        AvaliacaoDTO avaliacaoInvalida = new AvaliacaoDTO(0, null);
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, avaliacaoInvalida, null);

        mockMvc.perform(post("/avaliacao/cliente/concluir")
                        .with(authentication(authCliente()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando avaliacaoDTO do item for nula")
    void deveRetornar400ComAvaliacaoDTODoItemNula() throws Exception {
        ItemAvaliadoDTO itemInvalido = new ItemAvaliadoDTO(1L, 1L, null);
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, null, List.of(itemInvalido));

        mockMvc.perform(post("/avaliacao/cliente/concluir")
                        .with(authentication(authCliente()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando comentário do item estiver em branco")
    void deveRetornar400ComComentarioEmBranco() throws Exception {
        AvaliacaoDTO avaliacaoComComentarioVazio = new AvaliacaoDTO(5, "");
        ItemAvaliadoDTO itemDTO = new ItemAvaliadoDTO(1L, 1L, avaliacaoComComentarioVazio);
        ResponderAvaliacaoDTO dto = new ResponderAvaliacaoDTO(1L, null, List.of(itemDTO));

        mockMvc.perform(post("/avaliacao/cliente/concluir")
                        .with(authentication(authCliente()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
