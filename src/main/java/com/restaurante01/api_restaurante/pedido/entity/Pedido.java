package com.restaurante01.api_restaurante.pedido.entity;


import com.restaurante01.api_restaurante.pedido.Enum.StatusPedido;
import com.restaurante01.api_restaurante.security.Auditable;
import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
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
    @JoinColumn(name = "id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido statusPedido = StatusPedido.PENDENTE;

    // RELACIONAMENTO PAI -> FILHOS
    // CascadeType.ALL: Se eu salvar o Pedido, salva os Itens automaticamente.
    // orphanRemoval = true: Se eu remover um item da lista, ele é deletado do banco.
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itemPedido = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(nullable = false)
    private String enderecoEntrega;

    //criar metodos auxiliares da entidade, para garantir a qualidade dos dados e facilitar a vida do service
}
