package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.ObterProdutoPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CriarNovoPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorId;

    public CriarNovoPedidoCasoDeUso(PedidoRepositorio pedidoRepository, PedidoMapper pedidoMapper, ObterProdutoPorIdCasoDeUso obterProdutoPorId) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.obterProdutoPorId = obterProdutoPorId;
    }

    @Transactional
    public PedidoDTO executar(PedidoCriacaoDTO dto, Cliente cliente) {
        Pedido pedido = new Pedido();
        vincularItens(pedido, dto.itens());
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega("Endereço de teste"); // Futuramente vindo do DTO
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        Pedido pedidoSalvo = pedidoRepository.salvar(pedido);
        return pedidoMapper.mapearPedidoDto(pedidoSalvo);
    }

    private void vincularItens(Pedido pedido, List<ItemPedidoSolicitadoDTO> itensDto) {
        itensDto.forEach(item -> {
            Produto produto = obterProdutoPorId.retornarEntidade(item.idProduto());
            ItemPedido itemPedido = pedidoMapper.mapearItemPedido(item.quantidade(), produto);
            pedido.adicionarItem(itemPedido);
        });
    }
}