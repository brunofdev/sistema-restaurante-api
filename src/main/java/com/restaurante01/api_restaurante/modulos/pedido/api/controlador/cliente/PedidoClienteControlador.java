package com.restaurante01.api_restaurante.modulos.pedido.api.controlador.cliente;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Usuario;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.CriarNovoPedidoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.ListarPedidosPorClienteCasoDeUso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido/cliente")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "-> Pedidos - Cliente", description = "Ações exclusivas do aplicativo do cliente")
@SecurityRequirement(name = "bearerAuth")
public class PedidoClienteControlador {

    private final CriarNovoPedidoCasoDeUso criarNovoPedido;
    private final ListarPedidosPorClienteCasoDeUso listarPedidosPorCliente;
    private final SimpMessagingTemplate messagingTemplate;

    public PedidoClienteControlador(
            CriarNovoPedidoCasoDeUso criarNovoPedido,
            ListarPedidosPorClienteCasoDeUso listarPedidosPorCliente,
            SimpMessagingTemplate messagingTemplate) {
        this.criarNovoPedido = criarNovoPedido;
        this.listarPedidosPorCliente = listarPedidosPorCliente;
        this.messagingTemplate = messagingTemplate;
    }

    @Operation(summary = "Cria um novo pedido", description = "Registra um pedido para o cliente logado e notifica a administração via WebSocket.")
    @PostMapping("/criar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<PedidoCriadoDTO>> criarPedido(
            @Validated @RequestBody PedidoCriacaoDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado) throws JsonProcessingException {

        if (usuarioLogado instanceof Cliente clienteLogado) {
            PedidoCriadoDTO novoPedido = criarNovoPedido.executar(dto, clienteLogado);
            messagingTemplate.convertAndSend("/topico/admin-pedidos", novoPedido);
            return ResponseEntity.ok(ApiResponse.success("Recurso Criado", novoPedido));
        }
        throw new AccessDeniedException("Operadores não podem criar pedidos para si mesmos.");
    }

    @Operation(summary = "Listar pedidos do cliente logado", description = "Retorna o histórico de pedidos paginado do cliente autenticado.")
    @GetMapping("/historico")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Page<PedidoCriadoDTO>>> listarPedidosDoCliente(
            @ParameterObject
            @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        if (usuarioLogado instanceof Cliente cliente) {
            Page<PedidoCriadoDTO> paginaDePedidos = listarPedidosPorCliente.executar(cliente, pageable);
            return ResponseEntity.ok(ApiResponse.success("Recurso Obtido", paginaDePedidos));
        }
        throw new AccessDeniedException("Operadores não possuem histórico de pedidos pessoal.");
    }
}