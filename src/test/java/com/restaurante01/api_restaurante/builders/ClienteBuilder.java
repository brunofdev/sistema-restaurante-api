package com.restaurante01.api_restaurante.builders;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
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
    private String estado = "SC";
    private String cidade = "Florianopolis";
    private String bairro = "Centro";
    private String cep = "88000000";
    private String rua = "Rua Teste";
    private Integer numeroResidencia = 123;
    private String complemento = "Apto 101";
    private String observacaoEndereco = "Perto do mercado";

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

    public ClienteBuilder comEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public ClienteBuilder comCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public ClienteBuilder comBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public ClienteBuilder comCep(String cep) {
        this.cep = cep;
        return this;
    }

    public ClienteBuilder comRua(String rua) {
        this.rua = rua;
        return this;
    }

    public ClienteBuilder comNumeroResidencia(Integer numero) {
        this.numeroResidencia = numero;
        return this;
    }

    public ClienteBuilder comComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public ClienteBuilder comObservacaoEndereco(String obs) {
        this.observacaoEndereco = obs;
        return this;
    }

    public ClienteBuilder comPontuacao(int pontos) {
        this.pontuacaoFidelidade = pontos;
        return this;
    }

    // --- BUILD ---
    public Cliente build() {
        Cliente cliente = new Cliente();

        // Usuario
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setUserName(userName);
        cliente.setSenha(senha);
        cliente.setEmail(email);
        cliente.setCpf(cpf);
        cliente.setRole(role);
        cliente.setContaAtiva(contaAtiva);

        // Cliente
        cliente.setPontuacaoFidelidade(pontuacaoFidelidade);
        cliente.setTelefone(telefone);
        cliente.setEstado(estado);
        cliente.setCidade(cidade);
        cliente.setBairro(bairro);
        cliente.setCep(cep);
        cliente.setRua(rua);
        cliente.setNumeroResidencia(numeroResidencia);
        cliente.setComplemento(complemento);
        cliente.setObservacaoEndereco(observacaoEndereco);

        return cliente;
    }
}