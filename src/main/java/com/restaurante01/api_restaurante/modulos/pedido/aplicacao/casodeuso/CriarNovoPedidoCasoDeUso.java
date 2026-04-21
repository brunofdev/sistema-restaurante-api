package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoCupomPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesCupom;
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
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.EnderecoPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ProdutoVendido;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CriarNovoPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapeador pedidoMapeador;
    private final ApplicationEventPublisher eventPublisher;
    private final PedidoAssociacaoPorta produto;
    private final PedidoClientePorta pedidoClientePorta;
    private final PedidoCupomPorta pedidoCupomPorta;


    public CriarNovoPedidoCasoDeUso(PedidoRepositorio pedidoRepository,
                                    PedidoMapeador pedidoMapeador,
                                    ApplicationEventPublisher eventPublisher,
                                    PedidoAssociacaoPorta produto,
                                    PedidoClientePorta pedidoClientePorta,
                                    PedidoCupomPorta pedidoCupomPorta
                                        ) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapeador = pedidoMapeador;
        this.eventPublisher = eventPublisher;
        this.produto = produto;
        this.pedidoClientePorta = pedidoClientePorta;
        this.pedidoCupomPorta = pedidoCupomPorta;
    }

    @Transactional
    public PedidoCriadoDTO executar(PedidoCriacaoDTO dto, Cliente cliente) {
        produto.validarEstoque(dto.idCardapio(), pedidoMapeador.mapearParaValidacaoDeEstoque(dto.itens()));
        Pedido pedido = Pedido.criar(dto.idCardapio(), pedidoClientePorta.obterDetalhesClienteParaPedido(cliente), selecionaEndereco(dto, cliente));
        vincularItensAoPedido(pedido, dto.itens());
        vincularCupomAoPedido(dto.cupom(), pedido);
        pedidoRepository.salvar(pedido);
        eventPublisher.publishEvent(new PedidoCriadoEvento(pedido));
        return pedidoMapeador.mapearPedidoCriadoDto(pedido);
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
            InformacoesCupom cupom = pedidoCupomPorta.validarCupom(codigoCupom, pedido.getValorBruto()); //pode estourar excecao no modulo de cupom, caso o cupom seja invalido
            BigDecimal valorDesconto = calculaValorDesconto(cupom, pedido.getValorBruto());
            pedido.vincularCupom(cupom);
            pedido.aplicarDesconto(valorDesconto);
        }
    }

    private BigDecimal calculaValorDesconto(InformacoesCupom cupom, BigDecimal valorBrutoPedido){
        //se o cupom for invalido, cabe o modulo de cupom estourar uma excecao aqui
        return switch (cupom.regraDoCupom()) {
            case VALOR -> cupom.descontoDoCupom();
            case PORCENTAGEM ->
                    valorBrutoPedido.multiply(cupom.descontoDoCupom()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        };
    }
}