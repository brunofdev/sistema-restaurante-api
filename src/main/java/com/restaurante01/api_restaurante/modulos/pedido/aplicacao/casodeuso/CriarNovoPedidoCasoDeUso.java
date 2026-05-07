package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoCupomPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoOutboxPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.*;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.PedidoCriacaoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.*;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoAssociacaoPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoClientePorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CriarNovoPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapeador pedidoMapeador;
    private final ApplicationEventPublisher eventPublisher;
    private final PedidoAssociacaoPorta produto;
    private final PedidoClientePorta pedidoClientePorta;
    private final PedidoCupomPorta pedidoCupomPorta;
    private final PedidoOutboxPorta pedidoOutboxPorta;
    private final ObjectMapper objectMapper;


    public CriarNovoPedidoCasoDeUso(PedidoRepositorio pedidoRepository, PedidoMapeador pedidoMapeador, ApplicationEventPublisher eventPublisher, PedidoAssociacaoPorta produto, PedidoClientePorta pedidoClientePorta, PedidoCupomPorta pedidoCupomPorta, PedidoOutboxPorta pedidoOutboxPorta, ObjectMapper objectMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapeador = pedidoMapeador;
        this.eventPublisher = eventPublisher;
        this.produto = produto;
        this.pedidoClientePorta = pedidoClientePorta;
        this.pedidoCupomPorta = pedidoCupomPorta;
        this.pedidoOutboxPorta = pedidoOutboxPorta;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public PedidoCriadoDTO executar(PedidoCriacaoDTO dto, Cliente cliente) throws JsonProcessingException {
        produto.validarEstoque(dto.idCardapio(), pedidoMapeador.mapearParaValidacaoDeEstoque(dto.itens()));
        Pedido pedido = Pedido.criar(dto.idCardapio(), pedidoClientePorta.obterDetalhesClienteParaPedido(cliente), selecionaEndereco(dto, cliente));
        vincularItensAoPedido(pedido, dto.itens());
        vincularCupomAoPedido(dto.cupom(), pedido);
        Pedido pedidoSalvo = pedidoRepository.salvar(pedido);
        publicarEventos(pedidoSalvo);
        return pedidoMapeador.mapearPedidoCriadoDto(pedidoSalvo);
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
        return pedidoMapeador.mapearEndereco(dto.enderecoAlternativo());
    }

    private void vincularCupomAoPedido(String codigoCupom, Pedido pedido){
        if(codigoCupom != null && !codigoCupom.isBlank()){
            CupomConsumido cupom = pedidoCupomPorta.validarCupom(new CupomUtilizado(codigoCupom, pedido.getValorBruto(), pedidoRepository.encontrarPedidoComCupomRecorrente(pedido.getCliente().clienteId(), codigoCupom)));
            BigDecimal valorDesconto = cupom.regraDoCupom().aplicar(pedido.getValorBruto(), cupom.valorParaDesconto());
            pedido.vincularCupom(cupom);
            pedido.aplicarDesconto(valorDesconto);
        }
    }
    private void publicarEventos(Pedido pedidoSalvo) throws JsonProcessingException {
        List<ItemPedidoPayload> itemPedidoPayload = pedidoMapeador.mapearItemPedidoPayload(pedidoSalvo.getItens());
        eventPublisher.publishEvent(new PedidoCriadoEvento(pedidoSalvo, itemPedidoPayload));
        pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedidoSalvo.getId(), TipoEvento.BAIXAR_ESTOQUE_ASSOCIACAO, objectMapper.writeValueAsString(new PedidoCriadoPayload(pedidoSalvo.getId(), pedidoSalvo.getIdCardapio(), itemPedidoPayload)));
        pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedidoSalvo.getId(), TipoEvento.BAIXAR_ESTOQUE_PRODUTO, objectMapper.writeValueAsString(new PedidoCriadoPayload(pedidoSalvo.getId(), pedidoSalvo.getIdCardapio(), itemPedidoPayload)));
        if (pedidoSalvo.getCupom() != null) {
            pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedidoSalvo.getId(), TipoEvento.CONSUMIR_CUPOM, objectMapper.writeValueAsString(pedidoSalvo.getCupom().codigoCupom()));
        }
    }
}