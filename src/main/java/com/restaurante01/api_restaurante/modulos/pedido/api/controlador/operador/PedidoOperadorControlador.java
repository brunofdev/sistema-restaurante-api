package com.restaurante01.api_restaurante.modulos.pedido.api.controlador.operador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurante01.api_restaurante.compartilhado.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.SolicitarTopProdutoVendidosDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItensMaisVendidosPorPeriodo;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDetalhadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.AtualizarStatusPedidoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso.ListarMaisVendidosDaSemanaCasoDeUso;
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
@Tag(name = "-> Pedidos - Operador", description = "Visão gerencial e atualização de status de pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoOperadorControlador {

    private final ListarTodosPedidosCasoDeUso listarTodosPedidos;
    private final ListarPedidosDoDiaCasoDeUso listarPedidosDoDia;
    private final AtualizarStatusPedidoCasoDeUso atualizarStatusPedido;
    private final ListarMaisVendidosDaSemanaCasoDeUso listarMaisVendidosDaSemana;
    private final SimpMessagingTemplate messagingTemplate;

    public PedidoOperadorControlador(ListarTodosPedidosCasoDeUso listarTodosPedidos, ListarPedidosDoDiaCasoDeUso listarPedidosDoDia, AtualizarStatusPedidoCasoDeUso atualizarStatusPedido, ListarMaisVendidosDaSemanaCasoDeUso listarMaisVendidosDaSemana, SimpMessagingTemplate messagingTemplate) {
        this.listarTodosPedidos = listarTodosPedidos;
        this.listarPedidosDoDia = listarPedidosDoDia;
        this.atualizarStatusPedido = atualizarStatusPedido;
        this.listarMaisVendidosDaSemana = listarMaisVendidosDaSemana;
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

    @Operation(summary = "Listar produtos mais vendidos em um periodo", description = "Filtra e retorna  os produtos mais vendidos em um periodo, limita quantos quer que retorne.")
    @GetMapping("/top-produtos-vendidos")
    public ResponseEntity<ApiResponse<ItensMaisVendidosPorPeriodo>> listarTopProdutosVendidos(
            @ParameterObject
            @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.DESC)
            Pageable pageable,
            SolicitarTopProdutoVendidosDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", listarMaisVendidosDaSemana.executar(dto)));
    }

    @Operation(summary = "Atualizar status de um pedido", description = "Altera o status do pedido (ex: ENTREGUE) e dispara notificações WebSocket.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PedidoCriadoDTO>> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestBody StatusPedidoDTO novoStatus) throws JsonProcessingException {

        PedidoCriadoDTO pedidoAtualizado = atualizarStatusPedido.executar(id, novoStatus);

        // Notifica o cliente específico e o painel administrativo
        messagingTemplate.convertAndSend("/topico/pedido/" + id, pedidoAtualizado);
        messagingTemplate.convertAndSend("/topico/admin-pedidos", pedidoAtualizado);

        return ResponseEntity.ok(ApiResponse.success("Recurso Atualizado", pedidoAtualizado));
    }
}