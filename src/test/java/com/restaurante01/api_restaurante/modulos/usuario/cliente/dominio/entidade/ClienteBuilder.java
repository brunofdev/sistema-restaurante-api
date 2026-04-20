package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.PontuacaoFidelidade;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade.Email;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.role.Role;

public class ClienteBuilder {

    // --- CAMPOS HERDADOS (Usuario) ---
    private Long id = 1L;
    private String nome = "Cliente Teste";
    private String userName = "cliente.teste";
    private String senha = "123456";
    private String email = "cliente@email.com";
    private String cpf = "12345678901";
    private Role role = Role.USER;
    private boolean contaAtiva = true;

    // --- CAMPOS DO CLIENTE ---
    private int pontuacaoFidelidade = 0;
    private String telefone = "48999999999";

    // --- FACTORY METHOD ---
    public static ClienteBuilder umCliente() {
        return new ClienteBuilder();
    }

    // --- MÉTODOS FLUENTES (HERDADOS) ---
    public ClienteBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ClienteBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder comUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public ClienteBuilder comSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public ClienteBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public ClienteBuilder comCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public ClienteBuilder comRole(Role role) {
        this.role = role;
        return this;
    }

    public ClienteBuilder ativo() {
        this.contaAtiva = true;
        return this;
    }

    public ClienteBuilder inativo() {
        this.contaAtiva = false;
        return this;
    }

    // --- MÉTODOS FLUENTES (CLIENTE) ---
    public ClienteBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }



    public ClienteBuilder comPontuacao(int pontos) {
        this.pontuacaoFidelidade = pontos;
        return this;
    }

    // --- BUILD ---
    public Cliente build() {
        Cliente cliente =  new Cliente(
                id,
                nome,
                senha,
                new Email(email),
                new Cpf(cpf),
                role,
                true,
                PontuacaoFidelidade.criar(),
                telefone,
                new EnderecoCliente(
                "Tres pinheiros um",
                27,
                "mato grande",
                "canoas",
                "RS",
                "88058208",
                "casa",
                "51989043802")
        );

        return cliente;
    }
}