package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cliente.aplicacao.servico.ClienteService;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapper.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.Enum.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoException;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarStatusPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ClienteService clienteService;

    public AtualizarStatusPedidoCasoDeUso(PedidoRepositorio pedidoRepository, PedidoMapper pedidoMapper, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.clienteService = clienteService;
    }

    @Transactional
    public PedidoDTO executar(Long id, StatusPedidoDTO novoStatusDto) {
        Pedido pedido = pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não localizado: " + id));

        pedido.mudarStatus(novoStatusDto.statusPedido());
        pedidoRepository.salvar(pedido);

        // Regra de Negócio: Se entregue, atualiza fidelidade
        if (pedido.getStatusPedido() == StatusPedido.ENTREGUE) {
            clienteService.atualizaPontuacaoFidelidadeCliente(pedido.getCliente(), pedido.getValorTotal());
        }

        return pedidoMapper.mapearPedidoDto(pedido);
    }
}