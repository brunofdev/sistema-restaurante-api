package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.excecao.PontuacaoFidelidadeInvalidaExcecao;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class PontuacaoFidelidade {

    @Column(name = "ponto_fidelidade", nullable = false)
    private int valor;


    protected PontuacaoFidelidade() {}


    private PontuacaoFidelidade(int valor) {
        this.valor = valor;
    }
    public static PontuacaoFidelidade criar(){
        return new PontuacaoFidelidade(0);
    }

    public PontuacaoFidelidade acrescentar(int pontos) {
        if (pontos <= 0) throw new PontuacaoFidelidadeInvalidaExcecao("Pontos devem ser positivos");
        return new PontuacaoFidelidade(this.valor + pontos);
    }
}