package com.restaurante01.api_restaurante.usuarios.operador.entity;

import com.restaurante01.api_restaurante.usuarios.usuario_super.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operadores")
@Entity
public class Operador extends Usuario{
    @Column(name = "operador")
    @NotNull
    private Long operador;
}
