package com.restaurante01.api_restaurante.usuarios.cliente.entity;

import com.restaurante01.api_restaurante.usuarios.usuario_super.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Cliente extends Usuario {

    // --- REGRAS DE FIDELIDADE ---
    private static final BigDecimal LIMITE_FAIXA_BRONZE = BigDecimal.valueOf(50);
    private static final BigDecimal LIMITE_FAIXA_PRATA = BigDecimal.valueOf(90);
    private static final int PONTOS_BRONZE = 1;
    private static final int PONTOS_PRATA = 2;
    private static final int PONTOS_OURO = 3;

    // --- DADOS DO CLIENTE ---
    @Column(name = "ponto_fidelidade", nullable = false)
    private int pontuacaoFidelidade = 0; // Inicializa com 0

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos")
    @Column(nullable = false, length = 11)
    private String telefone;

    // --- ENDEREÇO ---

    @NotBlank
    @Size(max = 2)
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    @NotBlank
    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    @NotBlank
    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 dígitos numéricos")
    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @NotBlank
    @Column(name = "rua", nullable = false, length = 150)
    private String rua;

    @Column(name = "numero_residencia", nullable = false)
    private Integer numeroResidencia;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "obs_endereco", nullable = true)
    private String observacaoEndereco;


    // --- LÓGICA DE NEGÓCIO ---
    public void acrescentarPontuacao(BigDecimal totalPedido){
        if (totalPedido == null) return;
        this.pontuacaoFidelidade += calcularPontuacao(totalPedido);
    }

    private int calcularPontuacao(BigDecimal totalPedido) {
        if (totalPedido.compareTo(LIMITE_FAIXA_BRONZE) <= 0) {
            return PONTOS_BRONZE;
        } else if (totalPedido.compareTo(LIMITE_FAIXA_PRATA) <= 0) {
            return PONTOS_PRATA;
        }
        return PONTOS_OURO;
    }
}