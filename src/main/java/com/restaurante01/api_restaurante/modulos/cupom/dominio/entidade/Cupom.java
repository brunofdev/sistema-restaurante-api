package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;
import com.restaurante01.api_restaurante.infraestrutura.security.auditoria.Auditable;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Entity(name = "copons")
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = "id")
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
    @Column(name = "recorrencia", nullable = false)
    @Setter
    private RegraRecorrencia recorrencia;
    @Setter
    private boolean estaAtivo;


    public static Cupom criar(
            String codigoCupom,
            PeriodoCupom periodoCupom,
            boolean estaAtivo,
            int quantidade,
            TipoDesconto regraDesconto,
            RegraRecorrencia regraRecorrencia,
            BigDecimal valorParaDesconto,
            BigDecimal valorTotalMinPedido,
            BigDecimal valorTotalMaxPedido){
        Cupom cupom = new Cupom();
        cupom.adicionarCupom(codigoCupom);
        cupom.setPeriodoCupom(periodoCupom);
        cupom.setEstaAtivo(estaAtivo);
        cupom.setTipoDesconto(regraDesconto);
        cupom.setRecorrencia(regraRecorrencia);
        cupom.setQuantidade(quantidade);
        cupom.setValorParaDesconto(valorParaDesconto);
        cupom.setValorTotalMinPedido(valorTotalMinPedido);
        cupom.setValorTotalMaxPedido(valorTotalMaxPedido);
        verificaValorMinMenorQueValorMax(valorTotalMinPedido, valorTotalMaxPedido);
        return cupom;
    }
    public void atualizar(String novoCodigo, PeriodoCupom novoPeriodo,
                          int novaQuantidade, BigDecimal novoValorDesconto, boolean novoEstado,
                          BigDecimal novoValorTotalMin, BigDecimal novoTotalValorMax, TipoDesconto novoTipoDesconto) {
        adicionarCupom(novoCodigo);
        setPeriodoCupom(novoPeriodo);
        setEstaAtivo(novoEstado);
        setQuantidade(novaQuantidade);
        setValorParaDesconto(novoValorDesconto);
        setValorTotalMinPedido(novoValorTotalMin);
        setValorTotalMaxPedido(novoTotalValorMax);
        setTipoDesconto(novoTipoDesconto);
    }
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

    //Agrupar os VALORES em um VALOROBJECT que represente os tres valores, com regras proprias
    public void setValorParaDesconto(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorDescontoCupomExcecao("Valor atribuido para desconto deve ser uma Porcentagem ou Valor monetario, não pode ser zero ou negativo");
        }
        this.valorParaDesconto = valor;
    }
    public void setValorTotalMinPedido(BigDecimal valor){
        if(valor.compareTo(BigDecimal.valueOf(20)) < 0){
            throw new ValorMinPedidoExcecao("Valor total minimo do pedido não deve ser 20");
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
        if(this.valorTotalMinPedido.compareTo(valor) > 0){
            throw new ValorMaxPedidoExcecao("valorTotalMaxPedido não poder ser menor que o valoTotalMinPedido");
        }
        this.valorTotalMaxPedido = valor;

    }
    public boolean possuiQtdDisponivelParaConsumo(){
        return this.quantidade > 0;
    }
    private static void verificaValorMinMenorQueValorMax(BigDecimal valorTotalMinPedido, BigDecimal valorTotalMaxPedido){
        if(valorTotalMinPedido.compareTo(valorTotalMaxPedido) > 0){
            throw new CupomInvalidoExcecao("Valor min do pedido não pode ser maior que valor maximo do pedido");
        }
    }
    public void subtrairQuantidade(){
        if(this.quantidade <= 0){
            throw new CupomNaoPodeSerConsumidoExcecao("quantidade do cupom esta em zero ou negativa, nao pode diminuir");
        }
        this.quantidade -= 1;
        if(quantidade == 0){
            setEstaAtivo(false);
        }

    }

}
