package com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade;


import com.restaurante01.api_restaurante.modulos.pedido.dominio.Enum.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.StatusPedidoInvalidoException;
import com.restaurante01.api_restaurante.infraestrutura.security.auditoria.Auditable;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entity.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pedido extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido", nullable = false)
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
        if(!this.statusPedido.podeTransicionarPara(novoStatus)){
            throw  new StatusPedidoInvalidoException("Status do pedido não pode retroceder ou ser alterado caso este esteja cancelado");
        }
    }
}



