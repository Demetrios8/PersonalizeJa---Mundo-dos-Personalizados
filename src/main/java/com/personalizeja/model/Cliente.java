
package com.personalizeja.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String documento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    protected Cliente() {
    }

    public Cliente(
            String nome,
            String telefone,
            String email,
            String documento) {

        validarNome(nome);
        validarTelefone(telefone);
        validarEmail(email);
        validarDocumento(documento);

        this.nome = nome.trim();
        this.telefone = telefone.trim();
        this.email = normalizarEmail(email);
        this.documento = normalizarDocumento(documento);
        this.dataCadastro = LocalDateTime.now();
    }

    // Atualização dos dados

    public void atualizarDados(
            String nome,
            String telefone,
            String email,
            String documento) {

        validarNome(nome);
        validarTelefone(telefone);
        validarEmail(email);
        validarDocumento(documento);

        this.nome = nome.trim();
        this.telefone = telefone.trim();
        this.email = normalizarEmail(email);
        this.documento = normalizarDocumento(documento);
    }

    // Validações

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do cliente é obrigatório."
            );
        }
    }

    private void validarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException(
                    "O telefone do cliente é obrigatório."
            );
        }
    }

    private void validarEmail(String email) {
        if (email != null && !email.isBlank() &&
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException(
                    "O e-mail informado é inválido."
            );
        }
    }

    private void validarDocumento(String documento) {
        if (documento != null && documento.isBlank()) {
            throw new IllegalArgumentException(
                    "O documento não pode estar vazio."
            );
        }
    }

    private String normalizarEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }

        return email.trim().toLowerCase();
    }

    private String normalizarDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            return null;
        }

        return documento.trim();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
}
