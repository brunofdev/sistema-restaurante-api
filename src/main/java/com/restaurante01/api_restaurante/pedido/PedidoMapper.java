package com.restaurante01.api_restaurante.pedido;

import com.restaurante01.api_restaurante.pedido.dto.entrada.CriarPedidoDTO;
import com.restaurante01.api_restaurante.pedido.entity.Pedido;
import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public Pedido mapearPedido(CriarPedidoDTO dto, Usuario usuario){
        return new Pedido();
    }
}
