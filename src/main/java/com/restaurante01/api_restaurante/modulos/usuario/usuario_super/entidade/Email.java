package com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.EmailInvalidoExcecao;
import jakarta.persistence.Embeddable;

@Embeddable
public record Email(
        String email
) {

    public Email {
        if (email == null || email.isBlank())
            throw new EmailInvalidoExcecao("Email não pode ser vazio");
        if (!email.matches("^[\\w+.%-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
            throw new EmailInvalidoExcecao("Email inválido: " + email);
        email = email.toLowerCase().trim();
    }

    public String getEmail(){
        return email;
    }
}