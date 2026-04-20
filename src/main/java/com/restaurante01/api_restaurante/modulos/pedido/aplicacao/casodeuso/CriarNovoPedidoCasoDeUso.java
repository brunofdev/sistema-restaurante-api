package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;


import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.*;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoCardapioProdutoPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoClientePorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.EnderecoPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ProdutoVendido;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CriarNovoPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final PedidoCardapioProdutoPorta produto;
    private final PedidoClientePorta pedidoClientePorta;


    public CriarNovoPedidoCasoDeUso(PedidoRepositorio pedidoRepository,
                                    PedidoMapper pedidoMapper,
                                    ApplicationEventPublisher eventPublisher,
                                    PedidoCardapioProdutoPorta produto,
                                    PedidoClientePorta pedidoClientePorta
                                        ) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.eventPublisher = eventPublisher;
        this.produto = produto;
        this.pedidoClientePorta = pedidoClientePorta;
    }

    @Transactional
    public PedidoDTO executar(PedidoCriacaoDTO dto, Cliente cliente) {
        produto.validarEstoque(dto.idCardapio(), pedidoMapper.mapearParaValidacaoDeEstoque(dto.itens()));
        Pedido pedido = Pedido.criar(dto.idCardapio(), pedidoClientePorta.obterDetalhesClienteParaPedido(cliente), selecionaEndereco(dto, cliente));
        vincularItensAoPedido(pedido, dto.itens());
        pedidoRepository.salvar(pedido);
        eventPublisher.publishEvent(new PedidoCriadoEvento(pedido));
        return pedidoMapper.mapearPedidoDto(pedido);
    }

    private void vincularItensAoPedido(Pedido pedido, List<ItemPedidoSolicitadoDTO> itensDto) {
        itensDto.forEach(item -> {
            ProdutoVendido cardapioProduto = produto.obterProdutoVendido(pedido.getIdCardapio(), item.idProduto());
            ItemPedido itemPedido = ItemPedido.criar(pedido, item.quantidade(), cardapioProduto.produto() , cardapioProduto.precoDeVenda(), item.observacao());
            pedido.adicionarItem(itemPedido);
        });
    }
    private EnderecoPedido selecionaEndereco(PedidoCriacaoDTO dto, Cliente cliente){
        if(dto.enderecoAlternativo() == null){
            return pedidoClientePorta.obterEndereco(cliente);
        }
        return pedidoMapper.mapearEndereco(dto.enderecoAlternativo());
    }
}