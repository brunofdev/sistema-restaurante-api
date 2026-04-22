package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import com.restaurante01.api_restaurante.infraestrutura.security.auditoria.Auditable;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;



@NoArgsConstructor
@Entity(name = "copons")
@Getter
public class Cupom extends Auditable {

    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private CodigoCupom codigoCupom;
    @Column(name = "tipo_desconto", nullable = false)
    private TipoDesconto tipoDesconto;
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    @Column(name = "valor_desconto", nullable = false)
    private BigDecimal valorParaDesconto;
    @Column(name = "valor_min_pedido", nullable = false)
    private BigDecimal valorTotalMinPedido;
    @Column(name = "valor_max_pedido", nullable = false)
    private BigDecimal valorTotalMaxPedido;
    @Setter
    @Embedded
    PeriodoCupom periodoCupom;
    @Setter
    private boolean estaAtivo;

    public static Cupom criar(
            String codigoCupom,
            PeriodoCupom periodoCupom,
            boolean estaAtivo,
            int quantidade,
            TipoDesconto regra,
            BigDecimal valorParaDesconto,
            BigDecimal valorTotalMinPedido,
            BigDecimal valorTotalMaxPedido){
        Cupom cupom = new Cupom();
        cupom.adicionarCupom(codigoCupom);
        cupom.setPeriodoCupom(periodoCupom);
        cupom.setEstaAtivo(estaAtivo);
        cupom.setTipoDesconto(regra);
        cupom.setQuantidade(quantidade);
        cupom.setValorParaDesconto(valorParaDesconto);
        cupom.setValorTotalMinPedido(valorTotalMinPedido);
        cupom.setValorTotalMaxPedido(valorTotalMaxPedido);
        return cupom;
    }
//PRECISA CRIAR REGRA PARA DIMINUIR A QUANTIDADE APÓS O CONSUMO DO CUPOM
    public void adicionarCupom(String codigo){
        this.codigoCupom = new CodigoCupom(codigo);
    }
    public void setTipoDesconto(TipoDesconto regra){
        if(regra == null){
            throw  new RegraCupomInvalidaExcecao("Regra de desconto inválida");
        }
        this.tipoDesconto = regra;
    }
    public void setQuantidade(int quantidade){
        if (quantidade < 0 || quantidade > 100){
            throw new QtdCupomInvalidaExcecao("Quantidade não pode ser negativa ou maior que 100");
        }
        this.quantidade = quantidade;
    }
    //Melhorar regras de valores, pensar em criar um ValueObject com regras ou Definir regras no Enum
    public void setValorParaDesconto(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorDescontoCupomExcecao("Valor atribuido para desconto deve ser uma Porcentagem ou Valor monetario, não pode ser zero ou negativo");
        }
        this.valorParaDesconto = valor;
    }
    public void setValorTotalMinPedido(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) < 0){
            throw new ValorMinPedidoExcecao("Valor total minimo do pedido não deve ser 0");
        }
        if(valor.compareTo(BigDecimal.valueOf(40)) < 0){
            throw new ValorMinPedidoExcecao("Valor total minimo do pedido não deve ser 0");
        }
        this.valorTotalMinPedido = valor;
    }
    public void setValorTotalMaxPedido(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.valueOf(30)) <= 0){
            throw new ValorMaxPedidoExcecao("Valor total maximo do pedido não deve ser 0");
        }
        if(this.tipoDesconto == TipoDesconto.PORCENTAGEM && valor.compareTo(BigDecimal.valueOf(300)) > 0){
            throw new ValorMaxPedidoExcecao("Cupons com regra de desconto sob calculo de porcentagem não são aceitos em pedidos acima de R$300,00");
        }
        this.valorTotalMaxPedido = valor;
    }
}
