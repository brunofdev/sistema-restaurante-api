package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;


import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.adaptador.CardapioJpaAdaptador;
import org.springframework.stereotype.Service;

@Service
public class BuscarCardapioPorIdCasoDeUso {
    private final CardapioJpaAdaptador respositorio;

    public BuscarCardapioPorIdCasoDeUso(CardapioJpaAdaptador respositorio) {
        this.respositorio = respositorio;
    }

    public Cardapio executar(Long id) {
        return respositorio.findById(id)
                .orElseThrow(() -> new CardapioNaoEncontradoExcecao("Cardápio não encontrado"));
    }
}
