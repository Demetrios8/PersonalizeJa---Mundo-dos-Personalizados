
package com.personalizeja.model;

import java.time.LocalDateTime;

import com.personalizeja.enums.StatusAprovacao;

import jakarta.persistence.*;

@Entity
@Table(name = "artes")
public class Arte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Produto ao qual a arte pertence
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Pedido ao qual a arte personalizada pertence.
    // Nulo quando for uma arte padrão.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

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

    // Construtor para arte padrão
    public Arte(Produto produto, Integer versao, String arquivoUrl) {
        validarDados(produto, versao, arquivoUrl);

        this.produto = produto;
        this.versao = versao;
        this.arquivoUrl = arquivoUrl;
    }

    // Construtor para arte personalizada de um pedido
    public Arte(Produto produto, Pedido pedido,
                Integer versao, String arquivoUrl) {
        this(produto, versao, arquivoUrl);

        if (pedido == null) {
            throw new IllegalArgumentException(
                    "O pedido é obrigatório para uma arte personalizada."
            );
        }

        this.pedido = pedido;
    }

    @PrePersist
    private void aoCriar() {
        this.dataEnvio = LocalDateTime.now();
    }

    // Métodos de domínio

    public void iniciarAnalise() {
        if (this.status != StatusAprovacao.PENDENTE) {
            throw new IllegalStateException(
                    "Somente artes pendentes podem entrar em análise."
            );
        }

        this.status = StatusAprovacao.EM_ANALISE;
    }

    public void aprovar() {
        verificarEmAnalise();

        this.status = StatusAprovacao.APROVADA;
        this.dataAprovacao = LocalDateTime.now();
    }

    public void reprovar(String comentario) {
        verificarEmAnalise();
        validarComentario(comentario);

        this.status = StatusAprovacao.REPROVADA;
        this.comentarioCliente = comentario;
        this.dataAprovacao = null;
    }

    public void solicitarRevisao(String comentario) {
        verificarEmAnalise();
        validarComentario(comentario);

        this.status = StatusAprovacao.REVISAO_SOLICITADA;
        this.comentarioCliente = comentario;
        this.dataAprovacao = null;
    }

    // Controle do relacionamento com Pedido
    void definirPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    // Validações

    private void validarDados(
            Produto produto, Integer versao, String arquivoUrl) {

        if (produto == null) {
            throw new IllegalArgumentException(
                    "O produto é obrigatório."
            );
        }

        if (versao == null || versao <= 0) {
            throw new IllegalArgumentException(
                    "A versão deve ser maior que zero."
            );
        }

        if (arquivoUrl == null || arquivoUrl.isBlank()) {
            throw new IllegalArgumentException(
                    "O endereço do arquivo da arte é obrigatório."
            );
        }
    }

    private void verificarEmAnalise() {
        if (this.status != StatusAprovacao.EM_ANALISE) {
            throw new IllegalStateException(
                    "A arte precisa estar em análise para realizar esta operação."
            );
        }
    }

    private void validarComentario(String comentario) {
        if (comentario == null || comentario.isBlank()) {
            throw new IllegalArgumentException(
                    "O comentário é obrigatório."
            );
        }
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Integer getVersao() {
        return versao;
    }

    public String getArquivoUrl() {
        return arquivoUrl;
    }

    public StatusAprovacao getStatus() {
        return status;
    }

    public String getComentarioCliente() {
        return comentarioCliente;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }
}
