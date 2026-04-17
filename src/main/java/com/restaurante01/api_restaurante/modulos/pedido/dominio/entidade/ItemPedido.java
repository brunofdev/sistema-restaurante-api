package com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoException;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.ValorItemPedidoIncorretoExcecao;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoQntdNegativa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "observacao", nullable = true)
    private String observacao;

    //package protected para facilitar o builder nos testes
    ItemPedido(Long id, Pedido pedido, Produto produto,
               Integer quantidade, BigDecimal precoUnitario, String observacao) {
        this.id = id;
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.observacao = observacao;
    }

    public static ItemPedido criar(Pedido pedido, Integer quantidade, Produto produto, BigDecimal precoDeVenda, String observacao){
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.vincularPedido(pedido);
        itemPedido.vincularProduto(produto);
        itemPedido.setQuantidade(quantidade);
        itemPedido.adicionaPrecoDeVenda(precoDeVenda);
        itemPedido.setObservacao(observacao);
        return itemPedido;
    }

    public BigDecimal calcularSubTotal(){
        if(precoUnitario == null || quantidade == null){
            return BigDecimal.ZERO;
        }
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public void vincularPedido(Pedido pedido){
        if(pedido == null){
            throw new PedidoNaoEncontradoException("Pedido informado inexistes");
        }
        this.pedido = pedido;
    }
    public void vincularProduto(Produto produto){
        if(produto == null){
            throw new ProdutoNaoEncontradoException("Pedido informado inexistes");
        }
        this.produto = produto;
    }
    public void setQuantidade(Integer quantidade){
        if (quantidade == null || quantidade <= 0){
            throw new ProdutoQntdNegativa("Quantidade não aceita pelo sistema");
        }
        this.quantidade = quantidade;
    }
    public void adicionaPrecoDeVenda(BigDecimal precoDeVenda){
        if(precoDeVenda == null || precoDeVenda.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorItemPedidoIncorretoExcecao("Valor do item pedido não pode ser 0 ou nulo. Produto: " + this.getProduto().getNome());
        }
        this.precoUnitario = precoDeVenda;
    }
    public void setObservacao(String observacao){
        if (observacao == null || observacao.isBlank()){
            this.observacao = "Nenhuma observação informada";
            return;
        }
        this.observacao = observacao;
    }
}
