package com.restaurante01.api_restaurante.modulos.cardapio.api.controlador.privado;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacaoFeitaRespostaDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.AssociarProdutoAoCardapioCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.AtualizarCamposCustomDaAssociacaoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.DesassociarProdutoDoCardapioCasoDeUso;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.restaurante01.api_restaurante.infraestrutura.security.springsecurity.SecurityConfigurations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssociacaoOperadorControlador.class)
@Import(SecurityConfigurations.class)
class AssociacaoOperadorControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean
    AssociarProdutoAoCardapioCasoDeUso associarProduto;
    @MockitoBean
    AtualizarCamposCustomDaAssociacaoCasoDeUso atualizarCamposCustom;
    @MockitoBean DesassociarProdutoDoCardapioCasoDeUso desassociarProduto;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // Rota /cardapio-produto/operador/** exige no mínimo ROLE_ADMIN1 (SecurityConfigurations).

    private AssociacaoEntradaDTO dtoValido() {
        return new AssociacaoEntradaDTO(1L, 1L, new BigDecimal("25.00"), 100, "Descrição customizada", true, "Observação");
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve associar produto ao cardápio e retornar 201")
    void deveAssociarProdutoAoCardapio() throws Exception {
        var respostaEsperada = Instancio.create(AssociacaoFeitaRespostaDTO.class);
        when(associarProduto.executar(any())).thenReturn(respostaEsperada);

        mockMvc.perform(post("/cardapio-produto/operador/associar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("Recurso criado"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @DisplayName("Deve retornar 403 quando usuário não autenticado tentar associar produto")
    void deveRetornar403QuandoUsuarioSemPermissaoTentarAssociar() throws Exception {
        mockMvc.perform(post("/cardapio-produto/operador/associar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve retornar 400 quando os IDs do DTO forem nulos")
    void deveRetornar400QuandoIdsProdutoECardapioForemNulos() throws Exception {
        // idCardapio e idProduto nulos disparam @NotNull
        var dtoInvalido = new AssociacaoEntradaDTO(null, null, BigDecimal.ZERO, 0, "", false, "");

        mockMvc.perform(post("/cardapio-produto/operador/associar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve atualizar campos customizados da associação e retornar 200")
    void deveAtualizarCamposCustomizadosDaAssociacao() throws Exception {
        var respostaEsperada = Instancio.create(AssociacaoFeitaRespostaDTO.class);
        when(atualizarCamposCustom.executar(any())).thenReturn(respostaEsperada);

        mockMvc.perform(put("/cardapio-produto/operador/atualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso atualizado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve desassociar produto do cardápio e retornar 200")
    void deveDesassociarProdutoDoCardapio() throws Exception {
        doNothing().when(desassociarProduto).executar(anyLong(), anyLong());

        mockMvc.perform(delete("/cardapio-produto/operador/cardapio/1/produto/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso deletado"));
    }
}
