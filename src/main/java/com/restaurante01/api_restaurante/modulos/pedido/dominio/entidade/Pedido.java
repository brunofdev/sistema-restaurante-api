package com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade;


import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.EnderecoDoPedidoInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.StatusPedidoInvalidoExcecao;
import com.restaurante01.api_restaurante.infraestrutura.security.auditoria.Auditable;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.EnderecoPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesClienteParaPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.CupomConsumido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ValoresPedido;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Delegate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pedido extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private InformacoesClienteParaPedido cliente;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido", nullable = false)
    private StatusPedido statusPedido = StatusPedido.PENDENTE;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();
    @Column(nullable = true)
    @Embedded
    private EnderecoPedido enderecoPedidoEntrega;
    @Column(name = "cardapio_de_referencia", nullable = false)
    private Long idCardapio;
    @Embedded
    @Delegate
    private ValoresPedido valores = ValoresPedido.inicial();

    @Embedded
    private CupomConsumido cupom;

    public static Pedido criar(Long idCardapio, InformacoesClienteParaPedido cliente, EnderecoPedido enderecoPedido) {
        Pedido pedido = new Pedido();
        pedido.vincularCardapioPedido(idCardapio);
        pedido.vincularCliente(cliente);
        pedido.adicionarEndereco(enderecoPedido);
        pedido.aplicarDesconto(BigDecimal.ZERO);
        return pedido;
    }
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        calcularTotal();
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
        calcularTotal();
    }
    private void calcularTotal() {
        this.valores = valores.recalcular(itens);
    }
    public void mudarStatus(StatusPedido novoStatus) {
        if (!this.statusPedido.podeTransicionarPara(novoStatus)) {
            throw new StatusPedidoInvalidoExcecao("Status do pedido não pode retroceder ou ser alterado caso este esteja cancelado");
        }
        this.statusPedido = novoStatus;
    }
    private void vincularCardapioPedido(Long idCardapio) {
        if (idCardapio == null || idCardapio <= 0) {
            throw new CardapioNaoEncontradoExcecao("Id de cardapio nao pode ser vazio ou zero ou menor que zero, valor recebido = " + idCardapio);
        }
        this.idCardapio = idCardapio;
    }
    private void vincularCliente(InformacoesClienteParaPedido cliente) {
        if (cliente == null) {
            throw new StatusPedidoInvalidoExcecao("Erro ao vincular cliente, cliente invalido");
        }
        this.cliente = cliente;
    }
    public void adicionarEndereco(EnderecoPedido enderecoPedido) {
        if(enderecoPedido == null){
            throw new EnderecoDoPedidoInvalidoExcecao("Endereço adicionado no pedido está vazio");
        }
        this.enderecoPedidoEntrega = enderecoPedido;
    }
    public void vincularCupom(CupomConsumido cupom){
        this.cupom = cupom;
    }
    public void aplicarDesconto(BigDecimal desconto){
        this.valores = valores.aplicarDesconto(desconto);
    }
}



