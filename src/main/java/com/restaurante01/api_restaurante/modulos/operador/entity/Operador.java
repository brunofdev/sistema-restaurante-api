package com.restaurante01.api_restaurante.modulos.operador.entity;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.entidade.Usuario;
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
    @Column(name = "matricula", unique = true)
    @NotNull
    private Long matricula;
}
