package com.restaurante01.api_restaurante.produto.dto.entrada;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoDTO {
        @NotNull(message = "Preço deve ser informado")
        @Positive
        private Long id;
        @NotBlank(message = "Nome não deve ser vazio")
        private String nome;
        @NotBlank(message = "Descrição não deve ser vazia")
        private String descricao;
        @NotNull(message = "Preço deve ser informado")
        @PositiveOrZero(message = "Preço deve ser zero ou positivo")
        private BigDecimal preco;
        @NotNull(message = "Quantidade deve ser zero ou positivo")
        @Min(value = 0, message = "Quantidade minima deve ser zero")
        private Long quantidadeAtual;
        private Boolean disponibilidade;
         @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private LocalDateTime dataCriacao;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private LocalDateTime dataAtualizacao;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String criadoPor;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String atualizadoPor;


}
