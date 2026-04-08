package com.restaurante01.api_restaurante.modulos.pedido.api.controller;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.entidade.Usuario;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.*;
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
@RequestMapping("/pedido")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "Pedidos", description = "Gerenciamento do fluxo de pedidos e atualizações de status em tempo real")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    private final CriarNovoPedidoCasoDeUso criarNovoPedido;
    private final ListarPedidosPorClienteCasoDeUso listarPedidosPorCliente;
    private final ListarTodosPedidosCasoDeUso listarTodosPedidos;
    private final ListarPedidosDoDiaCasoDeUso listarPedidosDoDia;
    private final AtualizarStatusPedidoCasoDeUso atualizarStatusPedido;
    private final SimpMessagingTemplate messagingTemplate;


    public PedidoController(
            CriarNovoPedidoCasoDeUso criarNovoPedido,
            ListarPedidosPorClienteCasoDeUso listarPedidosPorCliente,
            ListarTodosPedidosCasoDeUso listarTodosPedidos,
            ListarPedidosDoDiaCasoDeUso listarPedidosDoDia,
            AtualizarStatusPedidoCasoDeUso atualizarStatusPedido,
            SimpMessagingTemplate messagingTemplate) {
        this.criarNovoPedido = criarNovoPedido;
        this.listarPedidosPorCliente = listarPedidosPorCliente;
        this.listarTodosPedidos = listarTodosPedidos;
        this.listarPedidosDoDia = listarPedidosDoDia;
        this.atualizarStatusPedido = atualizarStatusPedido;
        this.messagingTemplate = messagingTemplate;
    }

    @Operation(summary = "Cria um novo pedido", description = "Registra um pedido para o cliente logado e notifica a administração via WebSocket. Requer ROLE_USER.")
    @PostMapping("/criar-pedido")
    public ResponseEntity<ApiResponse<PedidoDTO>> criarPedido(
            @Validated @RequestBody PedidoCriacaoDTO dto,
            @AuthenticationPrincipal Cliente clienteLogado) {

        PedidoDTO novoPedido = criarNovoPedido.executar(dto, clienteLogado);
        messagingTemplate.convertAndSend("/topico/admin-pedidos", novoPedido);
        return ResponseEntity.ok(ApiResponse.success("Recurso Criado", novoPedido));
    }

    @Operation(summary = "Listar pedidos do cliente logado", description = "Retorna o histórico de pedidos paginado do cliente autenticado. Requer ROLE_USER.")
    @GetMapping("/obter-pedidos-do-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Page<PedidoDTO>>> listarPedidosDoCliente(
            @ParameterObject @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        if (usuarioLogado instanceof Cliente cliente) {
            Page<PedidoDTO> paginaDePedidos = listarPedidosPorCliente.executar(cliente, pageable);
            return ResponseEntity.ok(ApiResponse.success("Recurso Obtido", paginaDePedidos));
        }
        throw new AccessDeniedException("Operadores não possuem histórico de pedidos pessoal.");
    }

    @Operation(summary = "Listar todos os pedidos do sistema", description = "Retorna todos os pedidos registrados com paginação. Requer ROLE_USER.")
    @GetMapping("/obter-todos-pedidos")
    public ResponseEntity<ApiResponse<Page<PedidoDTO>>> listarPedidosFeitos(
            @ParameterObject @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PedidoDTO> paginaDePedidos = listarTodosPedidos.executar(pageable);
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", paginaDePedidos));
    }

    @Operation(summary = "Listar pedidos realizados hoje", description = "Filtra e retorna apenas os pedidos feitos na data atual. Requer ROLE_ADMIN1.")
    @GetMapping("/obter-pedidos-do-dia")
    public ResponseEntity<ApiResponse<Page<PedidoDTO>>> listarPedidosDoDiaAtual(
            @ParameterObject @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PedidoDTO> paginaDePedidos = listarPedidosDoDia.executar(pageable);
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", paginaDePedidos));
    }

    @Operation(summary = "Atualizar status de um pedido", description = "Altera o status do pedido (ex: ENTREGUE) e dispara notificações WebSocket para o cliente e admin. Requer ROLE_ADMIN1.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PedidoDTO>> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestBody StatusPedidoDTO novoStatus) {

        PedidoDTO pedidoAtualizado = atualizarStatusPedido.executar(id, novoStatus);

        // Notifica o cliente específico e o painel administrativo sobre a mudança
        messagingTemplate.convertAndSend("/topico/pedido/" + id, pedidoAtualizado);
        messagingTemplate.convertAndSend("/topico/admin-pedidos", pedidoAtualizado);

        return ResponseEntity.ok(ApiResponse.success("Recurso Atualizado", pedidoAtualizado));
    }
}