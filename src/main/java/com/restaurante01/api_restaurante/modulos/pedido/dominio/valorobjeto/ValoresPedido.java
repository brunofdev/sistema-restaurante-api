package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValoresPedido {

    @Column(name = "valor_bruto", nullable = false)
    private BigDecimal valorBruto;

    @Column(name = "desconto", nullable = false)
    private BigDecimal descontoAplicado;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    public static ValoresPedido inicial() {
        return new ValoresPedido(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public ValoresPedido recalcular(List<ItemPedido> itens) {
        BigDecimal bruto = itens.stream()
                .map(ItemPedido::calcularSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ValoresPedido(bruto, descontoAplicado, bruto.subtract(descontoAplicado));
    }

    public ValoresPedido aplicarDesconto(BigDecimal desconto) {
        return new ValoresPedido(valorBruto, desconto, valorBruto.subtract(desconto));
    }
}