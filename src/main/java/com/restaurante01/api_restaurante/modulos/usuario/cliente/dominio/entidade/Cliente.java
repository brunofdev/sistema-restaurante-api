package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.PontuacaoFidelidade;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Cliente extends Usuario {


    private PontuacaoFidelidade pontuacaoFidelidade;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos")
    @Column(nullable = false, length = 11)
    private String telefone;

    @Embedded
    EnderecoCliente enderecoCliente;



    public void acrescentarPontuacao(int pontos){
        this.pontuacaoFidelidade = this.pontuacaoFidelidade.acrescentar(pontos);
    }
}