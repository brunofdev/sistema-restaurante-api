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

        @Schema(description = "Sigla do Estado", example = "SP")
        @NotBlank(message = "O estado é obrigatório.")
        @Size(min = 2, max = 2, message = "Utilize a sigla do estado (ex: SP, RJ).")
        String estado,

        @Schema(description = "Nome da cidade", example = "São Paulo")
        @NotBlank(message = "A cidade é obrigatória.")
        String cidade,

        @Schema(description = "Nome do bairro", example = "Centro")
        @NotBlank(message = "O bairro é obrigatório.")
        String bairro,

        @Schema(description = "CEP do endereço", example = "01001000")
        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "O CEP deve estar no formato 00000-000 ou apenas números.")
        String cep,

        @Schema(description = "Nome da rua/logradouro", example = "Av. Paulista")
        @NotBlank(message = "A rua é obrigatória.")
        String rua,

        @Schema(description = "Número do endereço", example = "1000")
        @NotNull(message = "O número do endereço é obrigatório.")
        @Positive(message = "O número deve ser maior que zero.")
        Integer numero,

        @Schema(description = "Complemento do endereço (opcional)", example = "Apto 42")
        @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres.")
        String complemento
) {

    public CadastrarOperadorDTO withSenha(String senhaCriptografada) {

        return new CadastrarOperadorDTO(

                this.nome(),

                this.cpf(),

                this.email().toUpperCase(),

                this.telefone(),// <--- A única coisa que muda

                this.userName().toUpperCase(),

                senhaCriptografada,

                this.estado().toUpperCase(),

                this.cidade().toUpperCase(),

                this.bairro().toUpperCase(),

                this.cep(),

                this.rua().toUpperCase(),

                this.numero(),

                this.complemento().toUpperCase()

        );

    }

    @Override

    public String toString() {

        return "CadastrarUsuarioDTO[nome=" + nome + ", email=" + email + ", senha= ***** ]";

    }

}