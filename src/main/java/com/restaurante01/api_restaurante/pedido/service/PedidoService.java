package com.restaurante01.api_restaurante.pedido.service;


import com.restaurante01.api_restaurante.pedido.Enum.StatusPedido;
import com.restaurante01.api_restaurante.pedido.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.pedido.exception.PedidoNaoEncontradoException;
import com.restaurante01.api_restaurante.pedido.mapper.PedidoMapper;
import com.restaurante01.api_restaurante.pedido.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.pedido.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.pedido.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.pedido.entity.ItemPedido;
import com.restaurante01.api_restaurante.pedido.entity.Pedido;
import com.restaurante01.api_restaurante.pedido.repository.PedidoRepository;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.service.ProdutoService;
import com.restaurante01.api_restaurante.usuarios.cliente.entity.Cliente;
import com.restaurante01.api_restaurante.usuarios.usuario_super.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ProdutoService produtoService;
    public PedidoService (PedidoRepository pedidoRepository, PedidoMapper pedidoMapper, ProdutoService produtoService){
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.produtoService = produtoService;
    }
    public PedidoDTO criarNovoPedido(PedidoCriacaoDTO pedidoCriacaoDTO, Cliente cliente){
        Pedido pedido = new Pedido();
        montarPedido(pedido, cliente);
        vincularItemAoPedido(pedido, pedidoCriacaoDTO.itens());
        pedidoRepository.save(pedido);
        return pedidoMapper.mapearPedidoDto(pedido);
    }
    public PedidoDTO atualizarStatusPedido(Long id, StatusPedidoDTO novoStatus){
        Pedido pedido = encontrarPedidoPorId(id);
        pedido.mudarStatus(novoStatus.statusPedido());
        pedidoRepository.save(pedido);
        return pedidoMapper.mapearPedidoDto(pedido);
    }
    public Page<PedidoDTO> listarPedidos (Pageable pageable){
        return pedidoRepository.findAll(pageable)
                .map(pedidoMapper::mapearPedidoDto);
    }
    private void vincularItemAoPedido(Pedido pedido, List<ItemPedidoSolicitadoDTO> itens){
        itens.forEach(item -> {
            Produto produto = produtoService.encontrarProdutoPorId(item.idProduto());
            ItemPedido itemPedido = pedidoMapper.mapearItemPedido(item.quantidade(), produto);
            pedido.adicionarItem(itemPedido);
        });
    }
    private void montarPedido(Pedido pedido, Cliente cliente){
        cliente.acrescentarPontuacao(pedido.getValorTotal());
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega("Endereço de teste"); //ajustar para cliente poder informar endereço alternativo
        pedido.setStatusPedido(StatusPedido.PENDENTE);
    }
    private Pedido encontrarPedidoPorId(Long id){
       return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido com " + id + " não foi encontrado no sistema"));
    }
}
