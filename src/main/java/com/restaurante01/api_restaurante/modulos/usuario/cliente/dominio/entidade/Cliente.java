package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.PontuacaoFidelidade;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Email;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Usuario;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Entity
@Table(name = "clientes")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Cliente extends Usuario {


    private PontuacaoFidelidade pontuacaoFidelidade;

    @Setter
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos")
    @Column(nullable = false, length = 11)
    private String telefone;

    @Setter
    @Embedded
    EnderecoCliente enderecoCliente;

     Cliente(Long id, String nome, String senha, Email email, Cpf cpf, Role role, boolean contaAtiva, PontuacaoFidelidade pontuacaoFidelidade, String telefone, EnderecoCliente enderecoCliente) {
        super(id, nome, senha, email, cpf, role, contaAtiva);
        this.pontuacaoFidelidade = pontuacaoFidelidade;
        this.telefone = telefone;
        this.enderecoCliente = enderecoCliente;
    }

    public Cliente(String nome, String senha, Email email, Cpf cpf, Role role, boolean contaAtiva, PontuacaoFidelidade pontuacaoFidelidade, EnderecoCliente enderecoCliente, String telefone) {
        super(nome, senha, email, cpf, role, contaAtiva);
        this.pontuacaoFidelidade = pontuacaoFidelidade;
        this.enderecoCliente = enderecoCliente;
        this.telefone = telefone;
    }

    public static Cliente criar(String nome, String senha, Email email, Cpf cpf, EnderecoCliente enderecoCliente, String telefone){
        return new Cliente(
                nome,
                senha,
                email,
                cpf,
                Role.USER,
                true,
                PontuacaoFidelidade.criar(),
                enderecoCliente,
                telefone
        );
    }

    public void acrescentarPontuacao(int pontos){
        this.pontuacaoFidelidade = this.pontuacaoFidelidade.acrescentar(pontos);
    }
}