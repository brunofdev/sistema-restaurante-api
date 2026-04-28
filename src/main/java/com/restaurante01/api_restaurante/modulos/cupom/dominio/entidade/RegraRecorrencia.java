package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomNaoPodeSerConsumidoExcecao;
import java.time.LocalDateTime;

public enum RegraRecorrencia {
    DEZ_DIAS(10),
    QUINZE_DIAS(15),
    VINTE_DIAS(20),
    TRINTA_DIAS(30);

    private final int dias;

    RegraRecorrencia(int dias){ this.dias = dias;}

    public  void aplicar (LocalDateTime dataUltimoUso) {
        if (!dataUltimoUso.isBefore(LocalDateTime.now().minusDays(dias))) {
            throw new CupomNaoPodeSerConsumidoExcecao(
                    "Este cupom só pode ser reutilizado de " + dias + " em " + dias + " dias, seu último uso foi em: " + dataUltimoUso);
        }
    }
}
