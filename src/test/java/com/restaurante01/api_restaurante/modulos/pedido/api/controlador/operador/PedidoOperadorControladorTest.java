package com.restaurante01.api_restaurante.modulos.pedido.api.controlador.operador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.SolicitarTopProdutoVendidosDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItensMaisVendidosPorPeriodo;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDetalhadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.AtualizarStatusPedidoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.ListarMaisVendidosDaSemanaCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.ListarPedidosDoDiaCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.ListarTodosPedidosCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoOperadorControlador.class)
@Import(SecurityConfigurations.class)
class PedidoOperadorControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean ListarTodosPedidosCasoDeUso listarTodosPedidos;
    @MockitoBean ListarPedidosDoDiaCasoDeUso listarPedidosDoDia;
    @MockitoBean AtualizarStatusPedidoCasoDeUso atualizarStatusPedido;
    @MockitoBean ListarMaisVendidosDaSemanaCasoDeUso listarMaisVendidos;
    @MockitoBean SimpMessagingTemplate messagingTemplate;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // /pedido/operador/todos → ADMIN2
    // /pedido/operador/hoje → ADMIN1
    // /pedido/operador/top-produtos-vendidos → ADMIN1
    // /pedido/operador/{id}/status → ADMIN1

    @Test
    @WithMockUser(roles = "ADMIN2")
    @DisplayName("Deve listar todos os pedidos paginados quando autenticado como ADMIN2")
    void deveListarTodosOsPedidos() throws Exception {
        var pagina = new PageImpl<>(Instancio.ofList(PedidoDetalhadoDTO.class).size(3).create());
        when(listarTodosPedidos.executar(any())).thenReturn(pagina);

        mockMvc.perform(get("/pedido/operador/todos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso obtido"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve retornar 403 quando USER tentar listar todos os pedidos — requer ADMIN2")
    void deveRetornar403QuandoUserTentarListarTodosPedidos() throws Exception {
        mockMvc.perform(get("/pedido/operador/todos"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve listar pedidos do dia quando autenticado como ADMIN1")
    void deveListarPedidosDoDia() throws Exception {
        var pagina = new PageImpl<>(Instancio.ofList(PedidoCriadoDTO.class).size(2).create());
        when(listarPedidosDoDia.executar(any())).thenReturn(pagina);

        mockMvc.perform(get("/pedido/operador/hoje"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso obtido"));
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve listar os top produtos vendidos no período quando autenticado como ADMIN1")
    void deveListarTopProdutosVendidos() throws Exception {
        var resultado = Instancio.create(ItensMaisVendidosPorPeriodo.class);
        when(listarMaisVendidos.executar(any())).thenReturn(resultado);

        // Parâmetros enviados como query string — o controller usa SolicitarTopProdutoVendidosDTO como @ParameterObject
        mockMvc.perform(get("/pedido/operador/top-produtos-vendidos")
                .param("dataIni", LocalDate.now().minusDays(7).toString())
                .param("dataFim", LocalDate.now().toString())
                .param("quantidadeParaRetornar", "5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso obtido"));
    }

    @Test
    @WithMockUser(roles = "ADMIN1")
    @DisplayName("Deve atualizar status do pedido e notificar via WebSocket — retorna 200")
    void deveAtualizarStatusDoPedido() throws Exception {
        var novoStatus = new StatusPedidoDTO(StatusPedido.ENTREGUE);
        var pedidoAtualizado = Instancio.create(PedidoCriadoDTO.class);
        when(atualizarStatusPedido.executar(anyLong(), any())).thenReturn(pedidoAtualizado);

        mockMvc.perform(patch("/pedido/operador/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoStatus)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso Atualizado"))
            .andExpect(jsonPath("$.dados").exists());
    }
}
