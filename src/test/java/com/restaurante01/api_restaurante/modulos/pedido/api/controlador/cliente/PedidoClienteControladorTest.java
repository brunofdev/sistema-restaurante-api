package com.restaurante01.api_restaurante.modulos.pedido.api.controlador.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.infraestrutura.autenticacao.service.ServicoAutorizacao;
import com.restaurante01.api_restaurante.infraestrutura.jwt.JwtProvider;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.CriarNovoPedidoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.ListarPedidosPorClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.ClienteBuilder;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoClienteControlador.class)
@Import(SecurityConfigurations.class)
class PedidoClienteControladorTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean CriarNovoPedidoCasoDeUso criarNovoPedido;
    @MockitoBean ListarPedidosPorClienteCasoDeUso listarPedidosPorCliente;
    @MockitoBean SimpMessagingTemplate messagingTemplate;

    @MockitoBean JwtProvider jwtProvider;
    @MockitoBean ServicoAutorizacao servicoAutorizacao;

    // A rota /pedido/cliente/** exige ROLE_USER (SecurityConfigurations).
    //
    // IMPORTANTE: O controller faz `usuarioLogado instanceof Cliente` para extrair o cliente autenticado.
    // @WithMockUser injetaria um UserDetails genérico (User), não um Cliente — o instanceof falharia.
    // Por isso usamos .with(user(clienteReal)) para injetar uma instância real de Cliente como principal.

    @Test
    @DisplayName("Deve criar um pedido para o cliente autenticado e retornar 200")
    void deveCriarPedidoParaClienteAutenticado() throws Exception {
        var cliente = ClienteBuilder.umCliente().build();
        var dto = new PedidoCriacaoDTO(
            1L,
            List.of(new ItemPedidoSolicitadoDTO(1L, 2, "Sem cebola")),
            null,
            null
        );
        var pedidoCriado = Instancio.create(PedidoCriadoDTO.class);
        when(criarNovoPedido.executar(any(), any())).thenReturn(pedidoCriado);

        mockMvc.perform(post("/pedido/cliente/criar")
                .with(user(cliente))  // injeta Cliente real como principal
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso Criado"))
            .andExpect(jsonPath("$.dados").exists());
    }

    @Test
    @DisplayName("Deve retornar 403 quando tentar criar pedido sem autenticação")
    void deveRetornar403AoCriarPedidoSemAutenticacao() throws Exception {
        var dto = new PedidoCriacaoDTO(1L, List.of(new ItemPedidoSolicitadoDTO(1L, 1, null)), null, null);

        mockMvc.perform(post("/pedido/cliente/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve retornar o histórico de pedidos do cliente autenticado e retornar 200")
    void deveRetornarHistoricoDePedidosDoCliente() throws Exception {
        var cliente = ClienteBuilder.umCliente().build();
        var pagina = new PageImpl<>(Instancio.ofList(PedidoCriadoDTO.class).size(3).create());
        when(listarPedidosPorCliente.executar(any(), any())).thenReturn(pagina);

        mockMvc.perform(get("/pedido/cliente/historico")
                .with(user(cliente)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Recurso Obtido"));
    }
}
