package com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.NomeInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.SenhaInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String nome;
    @Column(name="senha", nullable = false)
    private String senha;
    @Embedded
    private Email email;
    @Embedded
    private Cpf cpf;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "conta_ativa", nullable = false)
    private boolean contaAtiva;

    public Usuario(String nome, String senha, Email email, Cpf cpf, Role role, boolean contaAtiva) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.cpf = cpf;
        this.role = role;
        this.contaAtiva = contaAtiva;
    }

    // --- MÉTODOS OBRIGATÓRIOS DO SPRING SECURITY (UserDetails) ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Se a role for nula, assume o mínimo possível (USER)
        if (this.role == null) return List.of(new SimpleGrantedAuthority("ROLE_USER"));

        switch (this.role) {
            case ADMIN3:
                // ADMIN3 manda em tudo: É Admin 3, 2, 1 e User ao mesmo tempo.
                return List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN3"),
                        new SimpleGrantedAuthority("ROLE_ADMIN2"),
                        new SimpleGrantedAuthority("ROLE_ADMIN1"),
                        new SimpleGrantedAuthority("ROLE_USER")
                );

            case ADMIN2:
                // ADMIN2 manda no nível dele para baixo: É Admin 2, 1 e User.
                return List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN2"),
                        new SimpleGrantedAuthority("ROLE_ADMIN1"),
                        new SimpleGrantedAuthority("ROLE_USER")
                );

            case ADMIN1:
                // ADMIN1 é Admin 1 e User.
                return List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN1"),
                        new SimpleGrantedAuthority("ROLE_USER")
                );
            default: // USER
                // Usuário comum só tem permissão básica.
                return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername(){return cpf.cpf();};

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new NomeInvalidoExcecao("Nome não pode ser vazio");
        }
        if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+"))
            throw new NomeInvalidoExcecao("Nome não pode conter números ou caracteres especiais");
        if (nome.trim().length() > 50){
            throw new NomeInvalidoExcecao("Nome não pode ter mais de que 50 caracteres");
        }
        this.nome = nome.trim();
    }

    public void setSenha(String senha) {
        if(senha == null || senha.isBlank()){
            throw new SenhaInvalidaExcecao("Senha não pode ser vazia");
        }
        this.senha = senha;
    }

}

