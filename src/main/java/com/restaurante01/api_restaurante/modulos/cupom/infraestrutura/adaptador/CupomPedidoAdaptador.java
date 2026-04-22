package com.restaurante01.api_restaurante.modulos.cupom.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.TipoDesconto;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoCupomPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesCupom;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CupomPedidoAdaptador implements PedidoCupomPorta {


    //APENAS TESTES CRIANDO PEDIDO POR  ENQUANTO
    @Override
    public InformacoesCupom validarCupom (String codigoCupom, BigDecimal valorPedido){
        //DADOS DE TESTE
        return new InformacoesCupom(
                23L,
                "DESCONTO10",
                new BigDecimal("19.60"),
                TipoDesconto.VALOR,
                "BrunoTeste"
        );
    }
}
