package com.restaurante01.api_restaurante.usuarios.entity;

import com.restaurante01.api_restaurante.usuarios.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String nome;
    @Column(name="user_name", nullable = false, unique = true)
    private String userName;
    @Column(name="password", nullable = false)
    private String senha;
    @Column(name="email", nullable = true, unique = true)
    private String email;
    @Column(name="cpf", nullable = true, unique = true)
    private String cpf;
    @Column(name = "telefone", nullable = true, unique = true)
    private String telefone;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "rua", nullable = true)
    private String rua;
    @Column(name = "cidade", nullable = true)
    private  String cidade;
    @Column(name = "cep", nullable = true)
    private long cep;
    @Column(name = "numero_residencia", nullable = true)
    private int numeroResidencia;
    @Column(name = "complemento", nullable = false)
    private String complemento;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserRole getRole(){
        return (this.role == null) ? UserRole.USER : this.role;
    }
}