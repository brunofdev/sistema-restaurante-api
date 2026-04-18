package com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.porta;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;

public interface CardapioPorta {
    Cardapio buscarCardapioPorId(Long id);
}
