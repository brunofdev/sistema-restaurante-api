package com.restaurante01.api_restaurante.usuarios.operador.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record CadastrarOperadorDTO(

        @Schema(description = "Nome completo do usuário", example = "Bruno de Fraga")
        @NotBlank(message = "O nome não pode estar em branco.")
        @Size(min = 5, max = 100, message = "O nome deve ter entre 5 e 100 caracteres.")
        @Pattern(regexp = "^[A-Za-zÀ-ú\\s'-]+$", message = "O nome deve conter apenas letras, espaços e hifens.")
        String nome,

        @Schema(description = "CPF sem pontuação", example = "12345678900")
        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
        String cpf,

        @Schema(description = "Endereço de e-mail válido", example = "bruno@email.com")
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O formato do e-mail é inválido.")
        String email,

        @Schema(description = "Telefone com DDD", example = "(11) 99999-9999")
        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$", message = "O telefone deve estar no formato (XX) 9XXXX-XXXX ou apenas números.")
        String telefone,

        @Schema(description = "Nome de usuário para login", example = "brunodev")
        @NotBlank(message = "O nome de usuário não pode estar em branco.")
        @Size(min = 5, max = 20, message = "O nome de usuário deve ter entre 5 e 20 caracteres.")
        @Pattern(regexp = "\\S+", message = "O nome de usuário não pode conter espaços em branco.")
        String userName,

        @Schema(description = "Senha forte (min 8 chars, 1 maiúscula, 1 minúscula, 1 número, 1 especial)", example = "Senha@123")
        @NotBlank(message = "A senha não pode estar em branco.")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve conter no mínimo 8 caracteres...")
        String senha,

        @Schema(description = "Matricula", example = "15487")
        @NotNull(message = "Matricula deve ser preenchida")
        @Min(value = 1000, message = "Matricula deve ter pelo menos 4 dígitos")
        @Max(value = 999999, message = "Matricula deve ter no máximo 6 dígitos")
        Long matricula
) {

    public CadastrarOperadorDTO withSenha(String senhaCriptografada) {
        return new CadastrarOperadorDTO(

                this.nome(),

                this.cpf(),

                this.email().toUpperCase(),

                this.telefone(),

                this.userName().toUpperCase(),

                senhaCriptografada,

               this.matricula

        );

    }

    @Override

    public String toString() {

        return "CadastrarUsuarioDTO[nome=" + nome + ", email=" + email + ", senha= ***** ]";

    }

}