package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Email;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Usuario;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Entity
@Table(name = "clientes")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Cliente extends Usuario {

    @Embedded
    private FidelidadeReferenciaId fidelidadeReferenciaId;

    @Setter
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos")
    @Column(nullable = false, length = 11)
    private String telefone;

    @Setter
    @Embedded
    EnderecoCliente enderecoCliente;

    Cliente(Long id, String nome, String senha, Email email, Cpf cpf, Role role, boolean contaAtiva, String telefone, EnderecoCliente enderecoCliente) {
        super(id, nome, senha, email, cpf, role, contaAtiva);
        this.telefone = telefone;
        this.enderecoCliente = enderecoCliente;
    }

    public static Cliente criar(String nome, String senha, Email email, Cpf cpf, EnderecoCliente enderecoCliente, String telefone) {
        return new Cliente(nome, senha, email, cpf, Role.CLIENT, true, enderecoCliente, telefone);
    }

    public Cliente(String nome, String senha, Email email, Cpf cpf, Role role, boolean contaAtiva, EnderecoCliente enderecoCliente, String telefone) {
        super(nome, senha, email, cpf, role, contaAtiva);
        this.enderecoCliente = enderecoCliente;
        this.telefone = telefone;
    }

    public void vincularFidelidade(Long fidelidadeId) {
        this.fidelidadeReferenciaId = FidelidadeReferenciaId.de(fidelidadeId);
    }
}