
package com.personalizeja.model;

import com.personalizeja.enums.PerfilUsuario;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilUsuario perfil;

    @Column(nullable = false)
    private boolean ativo;

    @OneToMany(mappedBy = "usuario")
    private List<MovimentacaoEstoque> movimentacoes =
            new ArrayList<>();

    protected Usuario() {
    }

    public Usuario(
            String nome,
            String email,
            String senha,
            PerfilUsuario perfil) {

        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarPerfil(perfil);

        this.nome = nome.trim();
        this.email = normalizarEmail(email);
        this.senha = senha;
        this.perfil = perfil;
        this.ativo = true;
    }

    // Atualização dos dados

    public void atualizarDados(String nome, String email) {

        validarNome(nome);
        validarEmail(email);

        this.nome = nome.trim();
        this.email = normalizarEmail(email);
    }

    public void alterarSenha(String novaSenha) {

        validarSenha(novaSenha);

        this.senha = novaSenha;
    }

    public void alterarPerfil(PerfilUsuario novoPerfil) {

        validarPerfil(novoPerfil);

        this.perfil = novoPerfil;
    }

    // Controle de ativação

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    // Validações

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do usuário é obrigatório."
            );
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank() ||
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException(
                    "O e-mail informado é inválido."
            );
        }
    }

    private void validarSenha(String senha) {
        if (senha == null || senha.length() < 8) {
            throw new IllegalArgumentException(
                    "A senha deve possuir pelo menos 8 caracteres."
            );
        }
    }

    private void validarPerfil(PerfilUsuario perfil) {
        if (perfil == null) {
            throw new IllegalArgumentException(
                    "O perfil do usuário é obrigatório."
            );
        }
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public List<MovimentacaoEstoque> getMovimentacoes() {
        return Collections.unmodifiableList(movimentacoes);
    }
}
