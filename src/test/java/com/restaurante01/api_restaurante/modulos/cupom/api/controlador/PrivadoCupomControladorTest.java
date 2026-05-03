package com.restaurante01.api_restaurante.modulos.cupom.api.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CriarCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.CupomAtualizadoDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.PeriodoCupomDTO;
import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.CupomAdminDTO;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.AtualizarCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.CriarCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.DeletarCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.ListarTodosCuponsCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.RegraRecorrencia;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrivadoCupomControlador.class)
@Import(SecurityConfigurations.class)
class PrivadoCupomControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean CriarCupomCasoDeUso criarCupom;
    @MockitoBean AtualizarCupomCasoDeUso atualizarCupom;
    @MockitoBean DeletarCupomCasoDeUso deletarCupom;
    @MockitoBean ListarTodosCuponsCasoDeUso listarTodosCupons;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // /cupom/admin/** → exige ADMIN1 (SecurityConfigurations)

    private CriarCupomDTO dtoCupomValido() {
        var periodo = new PeriodoCupomDTO("01/06/2026", "12:00", "30/06/2026", "23:59");
        return new CriarCupomDTO(
            "BEMVINDO10",
            TipoDesconto.PORCENTAGEM,
            RegraRecorrencia.QUINZE_DIAS,
            50,
            new BigDecimal("10.00"),
            new BigDecimal("50.00"),
            new BigDecimal("200.00"),
            true,
            periodo
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve criar cupom com dados válidos quando autenticado como ADMIN1 e retornar 200")
    void deveCriarCupomComDadosValidos() throws Exception {
        var cupomCriado = Instancio.create(CupomAdminDTO.class);
        when(criarCupom.executar(any())).thenReturn(cupomCriado);

        mockMvc.perform(post("/cupom/admin/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoCupomValido())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Cupom criado com sucesso"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @DisplayName("Deve retornar 403 quando tentar criar cupom sem autenticação")
    void deveRetornar403AoCriarCupomSemAutenticacao() throws Exception {
        mockMvc.perform(post("/cupom/admin/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoCupomValido())))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve retornar 400 quando o código do cupom estiver em branco")
    void deveRetornar400QuandoCodigoCupomEmBranco() throws Exception {
        // Enviando JSON vazio dispara todas as validações @NotBlank / @NotNull
        mockMvc.perform(post("/cupom/admin/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve listar todos os cupons quando autenticado como ADMIN1")
    void deveListarTodosOsCupons() throws Exception {
        var listaCupons = Instancio.ofList(CupomAdminDTO.class).size(3).create();
        when(listarTodosCupons.executar()).thenReturn(listaCupons);

        mockMvc.perform(get("/cupom/admin/listar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Cupons obtidos com sucesso"))
            .andExpect(jsonPath("$.dados").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve atualizar um cupom existente quando autenticado como ADMIN1")
    void deveAtualizarCupom() throws Exception {
        var dtoAtualizado = new CupomAtualizadoDTO(
            "DESCONTO15",
            TipoDesconto.PORCENTAGEM,
            30,
            new BigDecimal("15.00"),
            new BigDecimal("50.00"),
            new BigDecimal("300.00"),
            true,
            new PeriodoCupomDTO("01/07/2026", "08:00", "31/07/2026", "23:59")
        );
        var cupomAtualizado = Instancio.create(CupomAdminDTO.class);
        when(atualizarCupom.executar(anyLong(), any())).thenReturn(cupomAtualizado);

        mockMvc.perform(put("/cupom/admin/atualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoAtualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Cupom atualizado com sucesso"));
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve deletar cupom pelo ID quando autenticado como ADMIN1")
    void deveDeletarCupom() throws Exception {
        doNothing().when(deletarCupom).executar(anyLong());

        mockMvc.perform(delete("/cupom/admin/deletar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Cupom deletado com sucesso"));
    }
}
