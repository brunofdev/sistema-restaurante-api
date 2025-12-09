package com.restaurante01.api_restaurante.pedido.entity;


import com.restaurante01.api_restaurante.pedido.Enum.StatusPedido;
import com.restaurante01.api_restaurante.pedido.exception.StatusPedidoInvalidoException;
import com.restaurante01.api_restaurante.pedido.exception.StatusPedidoNaoPodeMaisSerAlteradoException;
import com.restaurante01.api_restaurante.security.auditoria.Auditable;
import com.restaurante01.api_restaurante.usuarios.usuario_super.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pedido extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido statusPedido = StatusPedido.PENDENTE;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(nullable = true)
    private String enderecoEntrega;

    public void adicionarItem(ItemPedido item){
        itens.add(item);
        item.setPedido(this);
        calcularTotal();
    }
    public void removerItem(ItemPedido item){
        itens.remove(item);
        item.setPedido(null);
        calcularTotal();
    }
    public void calcularTotal(){
       this.valorTotal = itens.stream()
               .map(ItemPedido::calcularSubTotal)
               .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    public void mudarStatus(StatusPedido novoStatus){
        if (this.statusPedido == StatusPedido.ENTREGUE || this.statusPedido == StatusPedido.CANCELADO) {
            throw new StatusPedidoNaoPodeMaisSerAlteradoException("Pedido finalizado não pode ser alterado.");
        }
        boolean transicaoValida = false;
        if (novoStatus == StatusPedido.CANCELADO) {
            transicaoValida = true;
        } else {
            switch (this.statusPedido) {
                case PENDENTE:
                    transicaoValida = (novoStatus == StatusPedido.EM_PREPARACAO);
                    break;
                case EM_PREPARACAO:
                    transicaoValida = (novoStatus == StatusPedido.SAIU_PARA_ENTREGA);
                    break;
                case SAIU_PARA_ENTREGA:
                    transicaoValida = (novoStatus == StatusPedido.ENTREGUE);
                    break;
            }
        }
        if (!transicaoValida) {
            throw new StatusPedidoInvalidoException(
                    "Não é possível mudar o status de " + this.statusPedido + " para " + novoStatus
            );
        }
        this.statusPedido = novoStatus;
    }
}



