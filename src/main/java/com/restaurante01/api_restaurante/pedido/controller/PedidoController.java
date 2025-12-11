package com.restaurante01.api_restaurante.pedido.controller;

import com.restaurante01.api_restaurante.core.utils.retorno_padrao_api.ApiResponse;
import com.restaurante01.api_restaurante.pedido.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.pedido.service.PedidoService;
import com.restaurante01.api_restaurante.pedido.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.pedido.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.usuarios.cliente.entity.Cliente;
import com.restaurante01.api_restaurante.usuarios.usuario_super.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pedido")
@Validated
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/criar-pedido")
    public ResponseEntity<ApiResponse<PedidoDTO>> criarPedido(@Validated @RequestBody PedidoCriacaoDTO dto, @AuthenticationPrincipal Cliente clienteLogado){
        PedidoDTO novoPedido = pedidoService.criarNovoPedido(dto, clienteLogado);
        messagingTemplate.convertAndSend("/topico/admin-pedidos", novoPedido);
        return ResponseEntity.ok(ApiResponse.success("Recurso Criado",novoPedido));
    }
    @GetMapping("obter-pedidos-do-cliente")
    @PreAuthorize("hasRole('USER')") // Mantém a segurança básica
    public ResponseEntity<ApiResponse<Page<PedidoDTO>>> listarPedidosDoCliente(
            @ParameterObject
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC)
            Pageable pageable,
            @AuthenticationPrincipal Usuario usuarioLogado)
    {
        if (usuarioLogado instanceof Cliente cliente) {
            Page<PedidoDTO> paginaDePedidos = pedidoService.listarPedidosDoCliente(pageable, cliente);
            return ResponseEntity.ok(ApiResponse.success("Recurso Obtido", paginaDePedidos));
        }
        throw new AccessDeniedException("Operadores não possuem histórico de pedidos pessoal.");
    }
    @GetMapping("obter-todos-pedidos")
    public ResponseEntity<ApiResponse<Page<PedidoDTO>>> listarPedidosFeitos(
            @ParameterObject
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC)
            Pageable pageable)
    {
        Page<PedidoDTO> paginaDePedidos = pedidoService.listarPedidos(pageable);
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", paginaDePedidos));
    }
    @GetMapping("obter-pedidos-do-dia")
    public ResponseEntity<ApiResponse<Page<PedidoDTO>>> listarPedidosDoDiaAtual(
            @ParameterObject
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC)
            Pageable pageable)
    {
        Page<PedidoDTO> paginaDePedidos = pedidoService.listarPedidosDoDia(pageable);
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", paginaDePedidos));
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PedidoDTO>> atualizarStatusPedido(@PathVariable Long id, @RequestBody StatusPedidoDTO novoStatus){
        PedidoDTO pedidoAtualizado = pedidoService.atualizarStatusPedido(id, novoStatus);
        messagingTemplate.convertAndSend("/topico/pedido/" + id , pedidoAtualizado);
        messagingTemplate.convertAndSend("/topico/admin-pedidos", pedidoAtualizado);
        return ResponseEntity.ok(ApiResponse.success("Recurso Atualizado" , pedidoAtualizado));
    }
}
