package com.restaurante01.api_restaurante.modulos.usuario.operador.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Usuario;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.role.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
