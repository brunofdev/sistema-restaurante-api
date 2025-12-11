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
import com.restaurante01.api_restaurante.usuarios.cliente.service.ClienteService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    public PedidoService (PedidoRepository pedidoRepository, PedidoMapper pedidoMapper, ProdutoService produtoService, ClienteService clienteService){
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }
    @Transactional
    public PedidoDTO criarNovoPedido(PedidoCriacaoDTO pedidoCriacaoDTO, Cliente cliente){
        Pedido pedido = new Pedido();
        vincularItemAoPedido(pedido, pedidoCriacaoDTO.itens());
        montarPedido(pedido, cliente);
        pedidoRepository.save(pedido);
        return pedidoMapper.mapearPedidoDto(pedido);
    }
    @Transactional
    public PedidoDTO atualizarStatusPedido(Long id, StatusPedidoDTO novoStatus){
        Pedido pedido = encontrarPedidoPorId(id);
        pedido.mudarStatus(novoStatus.statusPedido());
        pedidoRepository.save(pedido);
        verificaStatusPedidoEenviaFidelidade(pedido);
        return pedidoMapper.mapearPedidoDto(pedido);
    }
    private void verificaStatusPedidoEenviaFidelidade(Pedido pedido){
        if(pedido.getStatusPedido() == StatusPedido.ENTREGUE){
            clienteService.atualizaPontuacaoFidelidadeCliente(pedido.getCliente(), pedido.getValorTotal());
        }
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
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega("Endereço de teste"); //ajustar para cliente poder informar endereço alternativo
        pedido.setStatusPedido(StatusPedido.PENDENTE);
    }
    private Pedido encontrarPedidoPorId(Long id){
       return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido com " + id + " não foi encontrado no sistema"));
    }
    public Page<PedidoDTO> listarPedidosDoCliente(Pageable pageable, Cliente cliente){
        return pedidoRepository.findByCliente(cliente, pageable).map(pedidoMapper::mapearPedidoDto);
    }
    public Page<PedidoDTO> listarPedidosDoDia(Pageable pageable){
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioDoDia = hoje.atStartOfDay();
        LocalDateTime fimDoDia = hoje.atTime(23, 59, 59, 999999999);
        return pedidoRepository.findByDataCriacaoBetween(inicioDoDia, fimDoDia, pageable).map(pedidoMapper::mapearPedidoDto);
    }
}
