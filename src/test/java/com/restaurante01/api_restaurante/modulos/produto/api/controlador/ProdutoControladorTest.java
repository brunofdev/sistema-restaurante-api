package com.restaurante01.api_restaurante.modulos.produto.api.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.CriarProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada.ProdutoDTO;
import com.restaurante01.api_restaurante.modulos.produto.api.dto.saida.LoteProdutosRespostaDTO;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.*;
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

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoControlador.class)
@Import(SecurityConfigurations.class)
class ProdutoControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean CriarProdutoCasoDeUso criarProduto;
    @MockitoBean ObterTodosProdutosCasoDeUso obterTodosProdutos;
    @MockitoBean ObterProdutosDisponiveisCasoDeUso obterProdutosDisponiveis;
    @MockitoBean ObterProdutosIndisponiveisCasoDeUso obterProdutosIndisponiveis;
    @MockitoBean AtualizarProdutosEmLoteCasoDeUso atualizarProdutosEmLote;
    @MockitoBean DeletarProdutoCasoDeUso deletarProduto;
    @MockitoBean AtualizarUmProdutoCasoDeUso atualizarUmProduto;
    @MockitoBean ObterProdutoPorIdCasoDeUso obterProdutoPorId;
    @MockitoBean ObterProdutosBaixaQntdCasoDeUso obterProdutosBaixaQntd;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // /produtos/todos-produtos → PUBLIC (em PUBLIC_ENDPOINTS)
    // /produtos/adicionar-produto → exige ADMIN1 (SecurityConfigurations)
    // Demais rotas → anyRequest().hasRole("ADMIN3")

    @Test
    @DisplayName("Deve listar todos os produtos sem autenticação — rota pública")
    void deveListarTodosProdutosSemAutenticacao() throws Exception {
        var listaProdutos = Instancio.ofList(ProdutoDTO.class).size(3).create();
        when(obterTodosProdutos.executar()).thenReturn(listaProdutos);

        mockMvc.perform(get("/produtos/todos-produtos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso encontrado"))
            .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve buscar produto por ID quando autenticado como ADMIN3")
    void deveBuscarProdutoPorId() throws Exception {
        var produto = Instancio.create(ProdutoDTO.class);
        when(obterProdutoPorId.retornarDTO(anyLong())).thenReturn(produto);

        mockMvc.perform(get("/produtos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso encontrado"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve listar produtos disponíveis quando autenticado como ADMIN3")
    void deveListarProdutosDisponiveis() throws Exception {
        when(obterProdutosDisponiveis.executar()).thenReturn(List.of());

        mockMvc.perform(get("/produtos/produtos-disponiveis"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso encontrado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve listar produtos indisponíveis quando autenticado como ADMIN3")
    void deveListarProdutosIndisponiveis() throws Exception {
        when(obterProdutosIndisponiveis.executar()).thenReturn(List.of());

        mockMvc.perform(get("/produtos/produtos-indisponiveis"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso encontrado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve listar produtos com baixa quantidade quando autenticado como ADMIN3")
    void deveListarProdutosComBaixaQuantidade() throws Exception {
        when(obterProdutosBaixaQntd.executar()).thenReturn(List.of());

        mockMvc.perform(get("/produtos/produtos-com-baixa-quantidade"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso encontrado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve adicionar produto quando autenticado como ADMIN1")
    void deveAdicionarProduto() throws Exception {
        var dto = new CriarProdutoDTO("X-Bacon", "Hambúrguer especial", new BigDecimal("25.00"), 50, true);
        var produtoCriado = Instancio.create(ProdutoDTO.class);
        when(criarProduto.executar(any())).thenReturn(produtoCriado);

        mockMvc.perform(post("/produtos/adicionar-produto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso criado"));
    }

    @Test
    @DisplayName("Deve retornar 403 quando tentar adicionar produto sem autenticação")
    void deveRetornar403AoAdicionarProdutoSemAutenticacao() throws Exception {
        var dto = new CriarProdutoDTO("X-Bacon", "Hambúrguer especial", new BigDecimal("25.00"), 50, true);

        mockMvc.perform(post("/produtos/adicionar-produto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve atualizar um produto quando autenticado como ADMIN3")
    void deveAtualizarUmProduto() throws Exception {
        var dto = new ProdutoDTO(1L, "X-Bacon", "Hambúrguer especial", new BigDecimal("25.00"), 50, true, null, null, null, null);
        var produtoAtualizado = Instancio.create(ProdutoDTO.class);
        when(atualizarUmProduto.executar(any())).thenReturn(produtoAtualizado);

        mockMvc.perform(put("/produtos/atualizar-um-produto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Produto atualizado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve atualizar vários produtos em lote quando autenticado como ADMIN3")
    void deveAtualizarVariosProdutosEmLote() throws Exception {
        var dto = List.of(
            new ProdutoDTO(1L, "X-Bacon", "Descrição", new BigDecimal("25.00"), 10, true, null, null, null, null),
            new ProdutoDTO(2L, "X-Frango", "Descrição", new BigDecimal("22.00"), 20, true, null, null, null, null)
        );
        when(atualizarProdutosEmLote.executar(any())).thenReturn(dto);

        mockMvc.perform(put("/produtos/atualizar-varios-produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Atualização em lote realizada"));
    }

    @Test
    @WithMockUser(roles = "ADMIN3")
    @DisplayName("Deve deletar produto pelo ID quando autenticado como ADMIN3")
    void deveDeletarProduto() throws Exception {
        doNothing().when(deletarProduto).execute(anyLong());

        mockMvc.perform(delete("/produtos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso deletado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve retornar 400 quando adicionar produto com nome em branco")
    void deveRetornar400QuandoNomeProdutoEmBranco() throws Exception {
        // Nome vazio dispara @NotBlank
        var dto = new CriarProdutoDTO("", "Descrição válida", new BigDecimal("10.00"), 5, true);

        mockMvc.perform(post("/produtos/adicionar-produto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }
}
