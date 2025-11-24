package com.restaurante01.api_restaurante.usuarios.dto.entrada;

import com.restaurante01.api_restaurante.usuarios.enums.UserRole;
import jakarta.validation.constraints.*;

public record CadastrarUsuarioDTO(
        @NotBlank(message = "O nome não pode estar em branco.")
        @Size(min = 5, max = 100, message = "O nome deve ter entre 5 e 100 caracteres.")
        @Pattern(regexp = "^[A-Za-zÀ-ú\\s'-]+$", message = "O nome deve conter apenas letras, espaços e hifens.")
        String nome,

        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O formato do e-mail é inválido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$", message = "O telefone deve estar no formato (XX) 9XXXX-XXXX ou apenas números.")
        String telefone,


        @NotBlank(message = "O nome de usuário não pode estar em branco.")
        @Size(min = 5, max = 20, message = "O nome de usuário deve ter entre 5 e 20 caracteres.")
        @Pattern(regexp = "\\S+", message = "O nome de usuário não pode conter espaços em branco.")
        String userName,

        @NotBlank(message = "A senha não pode estar em branco.")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, com letra maiúscula, minúscula, número e caractere especial.")
        String senha,

        @NotBlank(message = "O estado é obrigatório.")
        @Size(min = 2, max = 2, message = "Utilize a sigla do estado (ex: SP, RJ).")
        String estado,

        @NotBlank(message = "A cidade é obrigatória.")
        String cidade,

        @NotBlank(message = "O bairro é obrigatório.")
        String bairro,

        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "O CEP deve estar no formato 00000-000 ou apenas números.")
        String cep,

        @NotBlank(message = "A rua é obrigatória.")
        String rua,

        @NotNull(message = "O número do endereço é obrigatório.")
        @Positive(message = "O número deve ser maior que zero.")
        Integer numero,

        // Sem validação de @NotBlank pois é opcional
        @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres.")
        String complemento
) {
    public CadastrarUsuarioDTO withSenha(String senhaCriptografada) {
        return new CadastrarUsuarioDTO(
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