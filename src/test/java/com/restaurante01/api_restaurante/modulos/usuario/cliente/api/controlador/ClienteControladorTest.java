package com.restaurante01.api_restaurante.modulos.usuario.cliente.api.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.entrada.CadastrarClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.AtualizarClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.CadastrarClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.DeletarClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.ListarTodosClientesCasoDeUso;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteControlador.class)
@Import(SecurityConfigurations.class)
class ClienteControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean CadastrarClienteCasoDeUso cadastrarCliente;
    @MockitoBean ListarTodosClientesCasoDeUso listarClientes;
    @MockitoBean AtualizarClienteCasoDeUso atualizarCliente;
    @MockitoBean DeletarClienteCasoDeUso deletarCliente;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // /cliente/cadastro → PUBLIC
    // /cliente/obter-todos → cai na regra geral anyRequest().hasRole("ADMIN3")
    // /cliente/{id} (PUT/DELETE) → cai na regra geral anyRequest().hasRole("ADMIN3")

    private CadastrarClienteDTO dtoClienteValido() {
        return new CadastrarClienteDTO(
            "Bruno de Fraga",
            "12345678901",
            "bruno@email.com",
            "(51) 99999-9999",
            "Senha@123",
            "RS",
            "Canoas",
            "Centro",
            "88058-208",
            "Rua Principal",
            100,
            "Apto 1",
            "Perto do mercado"
        );
    }

    @Test
    @DisplayName("Deve cadastrar cliente com dados válidos e retornar 201 — rota pública")
    void deveCadastrarCliente() throws Exception {
        var clienteCriado = Instancio.create(ClienteDTO.class);
        when(cadastrarCliente.executar(any())).thenReturn(clienteCriado);

        mockMvc.perform(post("/cliente/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoClienteValido())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("Recurso criado"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando o nome estiver em branco no cadastro de cliente")
    void deveRetornar400QuandoNomeEmBrancoNoCadastro() throws Exception {
        // Nome vazio dispara @NotBlank
        var dtoInvalido = new CadastrarClienteDTO(
            "",          // nome inválido
            "12345678901",
            "bruno@email.com",
            "(51) 99999-9999",
            "Senha@123",
            "RS", "Canoas", "Centro", "88058-208", "Rua Principal",
            100, "", ""
        );

        mockMvc.perform(post("/cliente/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve listar todos os clientes paginados quando autenticado como ADMIN3")
    void deveListarTodosOsClientes() throws Exception {
        var pagina = new PageImpl<>(Instancio.ofList(ClienteDTO.class).size(2).create());
        when(listarClientes.executar(any())).thenReturn(pagina);

        mockMvc.perform(get("/cliente/obter-todos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso disponível"));
    }

    @Test
    @DisplayName("Deve retornar 403 ao tentar listar clientes sem autenticação")
    void deveRetornar403AoListarClientesSemAutenticacao() throws Exception {
        mockMvc.perform(get("/cliente/obter-todos"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve atualizar dados de um cliente quando autenticado como ADMIN3")
    void deveAtualizarCliente() throws Exception {
        var clienteAtualizado = Instancio.create(ClienteDTO.class);
        when(atualizarCliente.executar(anyLong(), any())).thenReturn(clienteAtualizado);

        mockMvc.perform(put("/cliente/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteAtualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso atualizado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve deletar um cliente pelo ID quando autenticado como ADMIN3")
    void deveDeletarCliente() throws Exception {
        doNothing().when(deletarCliente).executar(anyLong());

        mockMvc.perform(delete("/cliente/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso deletado"));
    }
}
