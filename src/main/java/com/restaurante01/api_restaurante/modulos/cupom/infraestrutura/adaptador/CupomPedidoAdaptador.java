package com.restaurante01.api_restaurante.modulos.cupom.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.ValidarCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.Cupom;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoCupomPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.CupomUtilizado;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.CupomConsumido;
import org.springframework.stereotype.Component;

@Component
public class CupomPedidoAdaptador implements PedidoCupomPorta {

    private final ValidarCupomCasoDeUso validarCupomCasoDeUso;

    public CupomPedidoAdaptador(ValidarCupomCasoDeUso validarCupomCasoDeUso) {
        this.validarCupomCasoDeUso = validarCupomCasoDeUso;
    }

    @Override
    public CupomConsumido validarCupom (CupomUtilizado cupomUtilizado){
        Cupom cupom = validarCupomCasoDeUso.executar(cupomUtilizado.codigoCupom(), cupomUtilizado.valorBrutoTotalPedido());
        return new CupomConsumido(
                cupom.getId(),
                cupom.getCodigoCupom().getValor(),
                cupom.getValorParaDesconto(),
                cupom.getTipoDesconto(),
                cupom.getCriadoPor()
        );
    }
}
