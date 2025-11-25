package com.restaurante01.api_restaurante.pedido;


import com.restaurante01.api_restaurante.pedido.Enum.StatusPedido;
import com.restaurante01.api_restaurante.pedido.dto.entrada.CriarPedidoDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public PedidoDTO criarNovoPedido(CriarPedidoDTO pedidoDTO, Usuario usuario){
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        List<ItemPedido> itens = new ArrayList<>();
        for(ItemPedidoSolicitadoDTO dto : pedidoDTO.itens()){
            Produto produto = produtoRepository.findById(dto.idProduto()).orElseThrow(() -> new ProdutoNaoEncontradoException("Produto Não encontrado"));
            ItemPedido novoItem = new ItemPedido();
            novoItem.setProduto(produto);
            novoItem.setQuantidade(dto.quantidade());
            novoItem.setPrecoUnitario(produto.getPreco());
            pedido.adicionarItem(novoItem);
        }
        pedido.setEnderecoEntrega("Teste de endereço alternativo");
        pedido.calcularTotal();
        pedidoRepository.save(pedido);
        return  new PedidoDTO(
                pedido.getId(),
                pedido.getUsuario().getNome(),
                pedido.getValorTotal(),
                pedido.getStatusPedido()
        );
    }

    public List<PedidoDTO> listarPedidos (){
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDTO> dtos = new ArrayList<>();
        pedidos.stream().forEach(pedido  -> dtos.add(new PedidoDTO(pedido.getId(), pedido.getUsuario().getNome(), pedido.getValorTotal(), pedido.getStatusPedido())));
        return dtos;
    }

}
