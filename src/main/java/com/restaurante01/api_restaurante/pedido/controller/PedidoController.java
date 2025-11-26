package com.restaurante01.api_restaurante.pedido.controller;

import com.restaurante01.api_restaurante.core.utils.ApiResponse;
import com.restaurante01.api_restaurante.pedido.service.PedidoService;
import com.restaurante01.api_restaurante.pedido.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.pedido.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("**")
@RestController
@RequestMapping("/pedido")
@Validated
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/criar-pedido")
    public ResponseEntity<ApiResponse<PedidoDTO>> criarPedido(@Validated @RequestBody PedidoCriacaoDTO dto, @AuthenticationPrincipal Usuario usuarioLogado){
        return ResponseEntity.ok(ApiResponse.success("Recurso Criado", pedidoService.criarNovoPedido(dto, usuarioLogado)));

    }
    @GetMapping("obter-todos-pedidos")
    public ResponseEntity<ApiResponse<Page<PedidoDTO>>> listarPedidosFeitos(@PageableDefault(page = 0, size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC)
                                                                            Pageable pageable){
        Page<PedidoDTO> paginaDePedidos = pedidoService.listarPedidos(pageable);
        return ResponseEntity.ok(ApiResponse.success("Recurso obtido", paginaDePedidos));
    }
}
