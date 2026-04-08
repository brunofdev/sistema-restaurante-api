package com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.entidade.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Cliente extends Usuario {


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
    public void acrescentarPontuacao(int pontos){
        if (pontos <= 0) return;
        this.pontuacaoFidelidade += pontos;
    }
}