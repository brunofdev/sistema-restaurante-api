package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;


import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioNaoEncontradoException;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.adaptador.CardapioRepositorioAdaptador;
import org.springframework.stereotype.Service;

@Service
public class BuscarCardapioPorIdCasoDeUso {
    private final CardapioRepositorioAdaptador respositorio;

    public BuscarCardapioPorIdCasoDeUso(CardapioRepositorioAdaptador respositorio) {
        this.respositorio = respositorio;
    }

    public Cardapio executar(Long id) {
        return respositorio.findById(id)
                .orElseThrow(() -> new CardapioNaoEncontradoException("Cardápio não encontrado"));
    }
}
