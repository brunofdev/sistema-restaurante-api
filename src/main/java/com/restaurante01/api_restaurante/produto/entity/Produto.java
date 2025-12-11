package com.restaurante01.api_restaurante.produto.entity;

import com.restaurante01.api_restaurante.security.auditoria.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos") // Plural é convenção (opcional, mas recomendado)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Produto extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @PositiveOrZero(message = "O preço deve ser zero ou positivo")
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "A quantidade inicial é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Column(name = "qtd_atual", nullable = false)
    private Long quantidadeAtual = 0L;

    @NotNull
    @Column(nullable = false)
    private Boolean disponibilidade = true;

}