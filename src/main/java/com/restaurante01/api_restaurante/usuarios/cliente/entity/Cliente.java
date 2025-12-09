package com.restaurante01.api_restaurante.usuarios.cliente.entity;

import com.restaurante01.api_restaurante.usuarios.usuario_super.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
@Entity
public class Cliente extends Usuario {
    @Column(nullable = false)
    private int pontuacaoFidelidade;

    private static final BigDecimal LIMITE_FAIXA_BRONZE = BigDecimal.valueOf(50);
    private static final BigDecimal LIMITE_FAIXA_PRATA = BigDecimal.valueOf(90);

    private static final int PONTOS_BRONZE = 1;
    private static final int PONTOS_PRATA = 2;
    private static final int PONTOS_OURO = 3;


    //ajustar uma logica mais detalhada no futuro, mas é uma ideia legal
    public void acrescentarPontuacao(BigDecimal totalPedido){
        setPontuacaoFidelidade(getPontuacaoFidelidade() + calcularPontuacao(totalPedido));
    }
    private int calcularPontuacao(BigDecimal totalPedido) {
        if (totalPedido == null) {
            return 0;
        }
        if (totalPedido.compareTo(LIMITE_FAIXA_BRONZE) <= 0) {
            return PONTOS_BRONZE;
        }else if (totalPedido.compareTo(LIMITE_FAIXA_PRATA) <= 0) {
            return PONTOS_PRATA;
        }
        return PONTOS_OURO;
    }
}
