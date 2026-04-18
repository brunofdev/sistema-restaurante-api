package com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.BuscarCardapioPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.porta.CardapioPorta;
import org.springframework.stereotype.Component;

@Component
public class CardapioProdutoAdaptador implements CardapioPorta {

    private final BuscarCardapioPorIdCasoDeUso buscarCardapioPorIdCasoDeUso;

    public CardapioProdutoAdaptador(BuscarCardapioPorIdCasoDeUso buscarCardapioPorIdCasoDeUso) {
        this.buscarCardapioPorIdCasoDeUso = buscarCardapioPorIdCasoDeUso;
    }

    @Override
    public Cardapio buscarCardapioPorId(Long id){
        return buscarCardapioPorIdCasoDeUso.executar(id);
    }
}
