package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.*;
import jakarta.persistence.Embeddable;

@Embeddable
public record EnderecoCliente(
        String rua,
        Integer numero,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String complemento,
        String referencia
) {
    public EnderecoCliente {
        if (rua == null || rua.isBlank())
            throw new RuaInvalidaExcecao("Rua não pode ser vazia");

        if (numero == null)
            throw new NumeroInvalidoExcecao("Número não pode ser nulo");

        if (bairro == null || bairro.isBlank())
            throw new BairroInvalidoExcecao("Bairro não pode ser vazio");

        if (cidade == null || cidade.isBlank())
            throw new CidadeInvalidaExcecao("Cidade não pode ser vazia");

        if (estado == null || estado.isBlank())
            throw new EstadoInvalidoExcecao("Estado não pode ser vazio");

        if (cep == null || cep.isBlank())
            throw new CepInvalidoExcecao("CEP não pode ser vazio");

        if (complemento == null || complemento.isBlank())
            throw new ComplementoInvalidoExcecao("Complemento não pode ser vazio");

        // Normalização
        rua        = rua.trim();
        bairro     = bairro.trim();
        cidade     = cidade.trim();
        estado     = estado.trim();
        cep        = cep.replaceAll("[^\\d]", "");
        complemento = complemento.trim();
        referencia = (referencia != null && !referencia.isBlank()) ? referencia.trim() : null;
    }

    @Override
    public String toString() {
        return String.format("%s, nº %d, %s - %s, %s/%s, CEP: %s%s",
                rua, numero, complemento, bairro, cidade, estado, cep,
                (referencia != null ? " (" + referencia + ")" : ""));
    }
}

