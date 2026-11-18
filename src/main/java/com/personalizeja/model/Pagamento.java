
package com.personalizeja.model;

import com.personalizeja.enums.FormaPagamento;
import com.personalizeja.enums.StatusPagamento;
import com.personalizeja.enums.StatusPedido;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
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

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    protected Pagamento() {
    }

    public Pagamento(
            BigDecimal valor,
            FormaPagamento formaPagamento,
            Pedido pedido) {

        validarValor(valor);
        validarFormaPagamento(formaPagamento);
        validarPedido(pedido);

        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.pedido = pedido;
        this.statusPagamento = StatusPagamento.PENDENTE;
    }

    @PrePersist
    private void aoCriar() {
        this.dataCriacao = LocalDateTime.now();
    }

    // Atualização dos dados

    public void atualizarDados(
            BigDecimal valor,
            FormaPagamento formaPagamento) {

        verificarStatus(StatusPagamento.PENDENTE);

        validarValor(valor);
        validarFormaPagamento(formaPagamento);

        this.valor = valor;
        this.formaPagamento = formaPagamento;
    }

    // Métodos de alteração de estado

    public void aprovar() {

        verificarStatus(StatusPagamento.PENDENTE);

        this.statusPagamento = StatusPagamento.APROVADO;
        this.dataPagamento = LocalDateTime.now();
    }

    public void recusar() {

        verificarStatus(StatusPagamento.PENDENTE);

        this.statusPagamento = StatusPagamento.RECUSADO;
    }

    public void cancelar() {

        if (this.statusPagamento != StatusPagamento.PENDENTE &&
                this.statusPagamento != StatusPagamento.RECUSADO) {

            throw new IllegalStateException(
                    "Somente pagamentos pendentes ou recusados podem ser cancelados."
            );
        }

        this.statusPagamento = StatusPagamento.CANCELADO;
    }

    public void estornar() {

        verificarStatus(StatusPagamento.APROVADO);

        this.statusPagamento = StatusPagamento.ESTORNADO;
    }

    // Validações

    private void validarValor(BigDecimal valor) {

        if (valor == null ||
                valor.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "O valor do pagamento deve ser maior que zero."
            );
        }
    }

    private void validarFormaPagamento(FormaPagamento formaPagamento) {

        if (formaPagamento == null) {
            throw new IllegalArgumentException(
                    "A forma de pagamento é obrigatória."
            );
        }
    }

    private void validarPedido(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                    "O pedido é obrigatório."
            );
        }

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalArgumentException(
                    "Não é possível criar pagamento para um pedido cancelado."
            );
        }
    }

    private void verificarStatus(StatusPagamento esperado) {

        if (this.statusPagamento != esperado) {
            throw new IllegalStateException(
                    "Operação não permitida. Status atual: "
                            + this.statusPagamento
            );
        }
    }

    // Getters

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
