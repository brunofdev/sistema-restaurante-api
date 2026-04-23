package com.restaurante01.api_restaurante.modulos.pedido.api.controlador.operador;

import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDetalhadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.AtualizarStatusPedidoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.ListarPedidosDoDiaCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.ListarTodosPedidosCasoDeUso;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido/operador")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "7. Pedidos - Operador", description = "Visão gerencial e atualização de status de pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoOperadorControlador {

    private final ListarTodosPedidosCasoDeUso listarTodosPedidos;
    private final ListarPedidosDoDiaCasoDeUso listarPedidosDoDia;
    private final AtualizarStatusPedidoCasoDeUso atualizarStatusPedido;
    private final SimpMessagingTemplate messagingTemplate;

    public PedidoOperadorControlador(
            ListarTodosPedidosCasoDeUso listarTodosPedidos,
            ListarPedidosDoDiaCasoDeUso listarPedidosDoDia,
            AtualizarStatusPedidoCasoDeUso atualizarStatusPedido,
            SimpMessagingTemplate messagingTemplate) {
        this.listarTodosPedidos = listarTodosPedidos;
        this.listarPedidosDoDia = listarPedidosDoDia;
        this.atualizarStatusPedido = atualizarStatusPedido;
        this.messagingTemplate = messagingTemplate;
    }

    @Operation(summary = "Listar todos os pedidos do sistema", description = "Retorna todos os pedidos registrados com paginação.")
    @GetMapping("/todos")
    public ResponseEntity<ApiResponse<Page<PedidoDetalhadoDTO>>> listarPedidosFeitos(
            @ParameterObject
            @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PedidoDetalhadoDTO> paginaDePedidos = listarTodosPedidos.executar(pageable);
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", paginaDePedidos));
    }

    @Operation(summary = "Listar pedidos realizados hoje", description = "Filtra e retorna apenas os pedidos feitos na data atual.")
    @GetMapping("/hoje")
    public ResponseEntity<ApiResponse<Page<PedidoCriadoDTO>>> listarPedidosDoDiaAtual(
            @ParameterObject
            @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PedidoCriadoDTO> paginaDePedidos = listarPedidosDoDia.executar(pageable);
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", paginaDePedidos));
    }

    @Operation(summary = "Atualizar status de um pedido", description = "Altera o status do pedido (ex: ENTREGUE) e dispara notificações WebSocket.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PedidoCriadoDTO>> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestBody StatusPedidoDTO novoStatus) {

        PedidoCriadoDTO pedidoAtualizado = atualizarStatusPedido.executar(id, novoStatus);

        // Notifica o cliente específico e o painel administrativo
        messagingTemplate.convertAndSend("/topico/pedido/" + id, pedidoAtualizado);
        messagingTemplate.convertAndSend("/topico/admin-pedidos", pedidoAtualizado);

        return ResponseEntity.ok(ApiResponse.success("Recurso Atualizado", pedidoAtualizado));
    }
}