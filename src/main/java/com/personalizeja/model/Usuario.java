package com.personalizeja.model;

import com.personalizeja.enums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.AccessLevel;

@Entity
@Getter
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Getter(AccessLevel.NONE)
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilUsuario perfil;

    @Column(nullable = false)
    private boolean ativo;

    protected Usuario() {
    }

    public Usuario(String nome, String email, String senha,
                   PerfilUsuario perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.ativo = true;
    }

    public void atualizarDados(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public void alterarPerfil(PerfilUsuario novoPerfil) {
        this.perfil = novoPerfil;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
