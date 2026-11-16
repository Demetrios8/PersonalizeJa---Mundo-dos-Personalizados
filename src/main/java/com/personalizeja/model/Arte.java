package com.personalizeja.model;

import java.time.LocalDateTime;

import com.personalizeja.enums.StatusAprovacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "artes")
public class Arte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer versao;

    @Column(nullable = false)
    private String arquivoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAprovacao status = StatusAprovacao.PENDENTE;

    @Column(length = 500)
    private String comentarioCliente;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataEnvio;

    private LocalDateTime dataAprovacao;

    protected Arte() {
    }

    public Arte(Produto produto, Integer versao, String arquivoUrl) {
        this.produto = produto;
        this.versao = versao;
        this.arquivoUrl = arquivoUrl;
    }

    @PrePersist
    private void aoCriar() {
        this.dataEnvio = LocalDateTime.now();
    }

    // Métodos de domínio
    public void aprovar() {
        this.status = StatusAprovacao.APROVADA;
        this.dataAprovacao = LocalDateTime.now();
    }

    public void reprovar(String comentario) {
        this.status = StatusAprovacao.REPROVADA;
        this.comentarioCliente = comentario;
    }

    public void solicitarRevisao(String comentario) {
        this.status = StatusAprovacao.REVISAO_SOLICITADA;
        this.comentarioCliente = comentario;
    }

    // Getters
    public Long getId() { return id; }
    public Produto getProduto() { return produto; }
    public Integer getVersao() { return versao; }
    public String getArquivoUrl() { return arquivoUrl; }
    public StatusAprovacao getStatus() { return status; }
    public String getComentarioCliente() { return comentarioCliente; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public LocalDateTime getDataAprovacao() { return dataAprovacao; }
}
