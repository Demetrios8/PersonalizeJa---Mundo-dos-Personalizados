
package com.personalizeja.pedido.entity;

import com.personalizeja.arte.entity.Arte;
import com.personalizeja.cliente.entity.Cliente;
import com.personalizeja.pedido.enums.StatusPedido;
import com.personalizeja.pedido.enums.CanalOrigem;
import com.personalizeja.arte.enums.StatusAprovacao;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CanalOrigem canalOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemPedido> itens = new ArrayList<>();

    @OneToMany(mappedBy = "pedido")
    private List<Arte> artes = new ArrayList<>();

    protected Pedido() {
    }

    public Pedido(Cliente cliente, CanalOrigem canalOrigem) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "O cliente é obrigatório."
            );
        }

        if (canalOrigem == null) {
            throw new IllegalArgumentException(
                    "O canal de origem é obrigatório."
            );
        }

        this.cliente = cliente;
        this.canalOrigem = canalOrigem;
        this.dataPedido = LocalDateTime.now();
        this.status = StatusPedido.PENDENTE;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public CanalOrigem getCanalOrigem() {
        return canalOrigem;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public List<Arte> getArtes() {
        return Collections.unmodifiableList(artes);
    }

    // Métodos de alteração de estado

    public void iniciarProducao() {

        verificarStatus(StatusPedido.PENDENTE);
        verificarPossuiItens();
        verificarArtesAprovadas();

        this.status = StatusPedido.EM_PRODUCAO;
    }

    public void concluir() {

        verificarStatus(StatusPedido.EM_PRODUCAO);

        this.status = StatusPedido.CONCLUIDO;
    }

    public void entregar() {

        verificarStatus(StatusPedido.CONCLUIDO);

        this.status = StatusPedido.ENTREGUE;
    }

    public void cancelar() {

        if (this.status == StatusPedido.CONCLUIDO ||
                this.status == StatusPedido.ENTREGUE ||
                this.status == StatusPedido.CANCELADO) {

            throw new IllegalStateException(
                    "Não é possível cancelar este pedido."
            );
        }

        this.status = StatusPedido.CANCELADO;
    }

    // Gerenciamento dos itens

    public void adicionarItem(ItemPedido item) {

        verificarStatus(StatusPedido.PENDENTE);

        if (item == null) {
            throw new IllegalArgumentException(
                    "O item não pode ser nulo."
            );
        }

        if (item.getPedido() != null &&
                item.getPedido() != this) {

            throw new IllegalStateException(
                    "O item já pertence a outro pedido."
            );
        }

        if (!itens.contains(item)) {
            itens.add(item);
            item.definirPedido(this);
        }
    }

    public void removerItem(ItemPedido item) {

        verificarStatus(StatusPedido.PENDENTE);

        if (itens.remove(item)) {
            item.definirPedido(null);
        }
    }

    // Gerenciamento das artes

    public void adicionarArte(Arte arte) {

        verificarStatus(StatusPedido.PENDENTE);

        if (arte == null) {
            throw new IllegalArgumentException(
                    "A arte não pode ser nula."
            );
        }

        if (arte.getPedido() != null &&
                arte.getPedido() != this) {

            throw new IllegalStateException(
                    "A arte já pertence a outro pedido."
            );
        }

        if (!artes.contains(arte)) {
            artes.add(arte);
            arte.definirPedido(this);
        }
    }

    public void removerArte(Arte arte) {

        verificarStatus(StatusPedido.PENDENTE);

        if (artes.remove(arte)) {
            arte.definirPedido(null);
        }
    }

    // Cálculo do valor total

    public BigDecimal calcularValorTotal() {

        return itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Validações internas

    private void verificarStatus(StatusPedido esperado) {

        if (this.status != esperado) {
            throw new IllegalStateException(
                    "Operação não permitida. Status atual: "
                            + this.status
            );
        }
    }

    private void verificarPossuiItens() {

        if (itens.isEmpty()) {
            throw new IllegalStateException(
                    "O pedido precisa possuir pelo menos um item."
            );
        }
    }

    private void verificarArtesAprovadas() {

        boolean possuiArteNaoAprovada = artes.stream()
                .anyMatch(arte ->
                        arte.getStatus() != StatusAprovacao.APROVADA
                );

        if (possuiArteNaoAprovada) {
            throw new IllegalStateException(
                    "Todas as artes do pedido precisam estar aprovadas."
            );
        }
    }
}
