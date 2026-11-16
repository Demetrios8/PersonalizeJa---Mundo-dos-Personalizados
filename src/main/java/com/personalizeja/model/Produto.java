package com.personalizeja.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import java.util.ArrayList;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public LocalDateTime getDataFabricacao() { return dataFabricacao; }

    public boolean isAtivo() { return ativo; }
}