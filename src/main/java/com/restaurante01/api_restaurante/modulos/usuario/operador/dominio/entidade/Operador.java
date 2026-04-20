package com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Email;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Usuario;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.role.Role;
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
@Table(name = "operadores")
@Entity
public class Operador extends Usuario{

    public Operador(String nome, String senha, Email email, Cpf cpf, Role role, boolean contaAtiva) {
        super(nome, senha, email, cpf, role, contaAtiva);
    }

    public static Operador criar(String nome, String senha, Email email, Cpf cpf){
        return new Operador(nome, senha, email, cpf, Role.ADMIN3, true);
    }
}
