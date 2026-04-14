package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ObterProdutoValorCostumizadoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ValidarEstoquePedidoUseCase;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CriarNovoPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso;
    private final ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase;
    private final ApplicationEventPublisher eventPublisher;


    public CriarNovoPedidoCasoDeUso(PedidoRepositorio pedidoRepository,
                                    PedidoMapper pedidoMapper,
                                    ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase,
                                    ApplicationEventPublisher eventPublisher,
                                    ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.validarEstoquePedidoUseCase = validarEstoquePedidoUseCase;
        this.eventPublisher = eventPublisher;
        this.obterProdutoValorCostumizadoCasoDeUso = obterProdutoValorCostumizadoCasoDeUso;
    }

    @Transactional
    public PedidoDTO executar(PedidoCriacaoDTO dto, Cliente cliente) {
        List<CardapioProduto> estoqueValidado = validarEstoquePedidoUseCase.executar(dto);
        Pedido pedido = new Pedido();
        vincularItensAoPedido(pedido, dto.itens(), dto.idCardapio());
        pedido.vincularCliente(cliente);
        pedido.setEnderecoEntrega("Endereço de teste");
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedidoRepository.salvar(pedido);
        eventPublisher.publishEvent(new PedidoCriadoEvento(pedido, estoqueValidado, dto.idCardapio()));
        return pedidoMapper.mapearPedidoDto(pedido);
    }

    private void vincularItensAoPedido(Pedido pedido, List<ItemPedidoSolicitadoDTO> itensDto) {
        itensDto.forEach(item -> {
            CardapioProduto cardapioProduto = obterProdutoValorCostumizadoCasoDeUso.executar(pedido.getIdCardapio(), item.idProduto());
            ItemPedido itemPedido = pedidoMapper.mapearItemPedido(item.quantidade(), cardapioProduto, item.observacao());
            pedido.adicionarItem(itemPedido);
        });
    }
}