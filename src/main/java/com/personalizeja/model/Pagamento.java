package com.personalizeja.model;

import com.personalizeja.enums.FormaPagamento;
import com.personalizeja.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento statusPagamento;

    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    protected Pagamento() {
    }

    public Pagamento(
            BigDecimal valor,
            FormaPagamento formaPagamento,
            Pedido pedido
    ) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor do pagamento deve ser maior que zero."
            );
        }

        if (formaPagamento == null) {
            throw new IllegalArgumentException(
                    "A forma de pagamento é obrigatória."
            );
        }

        if (pedido == null) {
            throw new IllegalArgumentException(
                    "O pedido é obrigatório."
            );
        }

        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.pedido = pedido;
        this.statusPagamento = StatusPagamento.PENDENTE;
        this.dataPagamento = LocalDateTime.now();
    }

    public void atualizarDados(
            BigDecimal valor,
            FormaPagamento formaPagamento
    ) {
        if (this.statusPagamento != StatusPagamento.PENDENTE) {
            throw new IllegalStateException(
                    "Somente pagamentos pendentes podem ser alterados."
            );
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor do pagamento deve ser maior que zero."
            );
        }

        if (formaPagamento == null) {
            throw new IllegalArgumentException(
                    "A forma de pagamento é obrigatória."
            );
        }

        this.valor = valor;
        this.formaPagamento = formaPagamento;
    }

    public void aprovar() {
        if (this.statusPagamento != StatusPagamento.PENDENTE) {
            throw new IllegalStateException(
                    "Somente pagamentos pendentes podem ser aprovados."
            );
        }

        this.statusPagamento = StatusPagamento.APROVADO;
    }

    public void recusar() {
        if (this.statusPagamento != StatusPagamento.PENDENTE) {
            throw new IllegalStateException(
                    "Somente pagamentos pendentes podem ser recusados."
            );
        }

        this.statusPagamento = StatusPagamento.RECUSADO;
    }

    public void cancelar() {
        if (this.statusPagamento == StatusPagamento.APROVADO
                || this.statusPagamento == StatusPagamento.ESTORNADO) {
            throw new IllegalStateException(
                    "Este pagamento não pode ser cancelado."
            );
        }

        this.statusPagamento = StatusPagamento.CANCELADO;
    }

    public void estornar() {
        if (this.statusPagamento != StatusPagamento.APROVADO) {
            throw new IllegalStateException(
                    "Somente pagamentos aprovados podem ser estornados."
            );
        }

        this.statusPagamento = StatusPagamento.ESTORNADO;
    }
}