package com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade;

import com.restaurante01.api_restaurante.infraestrutura.security.auditoria.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cardapios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Cardapio extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "O nome do cardápio é obrigatório")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "disponibilidade", nullable = false)
    private Boolean disponibilidade = true;

    @NotNull
    @Column(name = "dtinicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "dtfim")
    private LocalDate dataFim;


    public boolean estaAtivo() {
        if (Boolean.FALSE.equals(this.disponibilidade)) return false;
        LocalDate hoje = LocalDate.now();
        boolean iniciou = !hoje.isBefore(this.dataInicio);
        boolean naoAcabou = this.dataFim == null || !hoje.isAfter(this.dataFim);
        return iniciou && naoAcabou;
    }
}