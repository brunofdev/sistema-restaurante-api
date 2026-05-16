package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade;

public class FidelidadeBuilder {

    private Long clienteId = 1L;

    private FidelidadeBuilder() {}

    public static FidelidadeBuilder umaFidelidade() {
        return new FidelidadeBuilder();
    }

    public FidelidadeBuilder semCliente() {
        this.clienteId = null;
        return this;
    }

    public Fidelidade construir() {
        return Fidelidade.criar(this.clienteId);
    }
}
