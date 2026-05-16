package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class FidelidadeReferenciaId {

    @Column(name = "fidelidade_referencia_id")
    private Long valor;

    protected FidelidadeReferenciaId() {}

    private FidelidadeReferenciaId(Long valor) {
        this.valor = valor;
    }

    public static FidelidadeReferenciaId de(Long fidelidadeId) {
        if (fidelidadeId == null) throw new IllegalArgumentException("ID de fidelidade não pode ser nulo");
        return new FidelidadeReferenciaId(fidelidadeId);
    }
}
