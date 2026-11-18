package com.personalizeja.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(unique = true)
    private String codigoBarras;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataFabricacao;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Arte> artes = new ArrayList<>();

    protected Produto() {
    }

    public Produto(String nome, String descricao, BigDecimal preco, String codigoBarras) {
        validarNome(nome);
        validarPreco(preco);

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.codigoBarras = codigoBarras;
    }

    @PrePersist
    private void aoCriar() {
        if (this.dataFabricacao == null) {
            this.dataFabricacao = LocalDateTime.now();
        }
    }

    // Métodos modificadores de estado

    public void atualizarDados(String nome, String descricao, BigDecimal preco, String codigoBarras) {
        validarNome(nome);
        validarPreco(preco);

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.codigoBarras = codigoBarras;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    // Validações

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
    }

    // Getters

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
    public String getCodigoBarras() { return codigoBarras; }
    public LocalDateTime getDataFabricacao() { return dataFabricacao; }
    public boolean isAtivo() { return ativo; }
}