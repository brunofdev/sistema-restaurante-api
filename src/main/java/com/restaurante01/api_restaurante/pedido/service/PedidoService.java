package com.restaurante01.api_restaurante.pedido.service;


import com.restaurante01.api_restaurante.pedido.Enum.StatusPedido;
import com.restaurante01.api_restaurante.pedido.mapper.PedidoMapper;
import com.restaurante01.api_restaurante.pedido.dto.entrada.pedidoCriacaoDTO;
import com.restaurante01.api_restaurante.pedido.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.pedido.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.pedido.entity.ItemPedido;
import com.restaurante01.api_restaurante.pedido.entity.Pedido;
import com.restaurante01.api_restaurante.pedido.repository.PedidoRepository;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoNaoEncontradoException;
import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;
import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ProdutoRepository produtoRepository;

    public PedidoService (PedidoRepository pedidoRepository, PedidoMapper pedidoMapper, ProdutoRepository produtoRepository){
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.produtoRepository = produtoRepository;
    }

    public PedidoDTO criarNovoPedido(pedidoCriacaoDTO pedidoCriacaoDTO, Usuario usuario){
        Pedido pedido = new Pedido();
        List<ItemPedido> itens = new ArrayList<>();
        pedidoCriacaoDTO.itens().forEach(dto -> {
            Produto produto = produtoRepository.findById(dto.idProduto()).orElseThrow(() -> new ProdutoNaoEncontradoException("Produto Não encontrado"));
            ItemPedido item = pedidoMapper.mapearItemPedido(dto.quantidade(), produto);
            pedido.adicionarItem(item);
        });
        pedidoMapper.mapearPedido(pedido, usuario);
        pedidoRepository.save(pedido);
        return pedidoMapper.mapearPedidoDto(pedido);
    }

    public List<PedidoDTO> listarPedidos (){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.mapearListaPedidoDTO(pedidos);
    }
}
