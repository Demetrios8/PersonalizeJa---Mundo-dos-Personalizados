
package com.personalizeja.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(length = 500)
    private String descricaoPersonalizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    protected ItemPedido() {
    }

    public ItemPedido(
            Produto produto,
            Integer quantidade,
            BigDecimal precoUnitario,
            String descricaoPersonalizacao) {

        if (produto == null) {
            throw new IllegalArgumentException(
                    "O produto é obrigatório."
            );
        }

        validarQuantidade(quantidade);
        validarPreco(precoUnitario);

        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.descricaoPersonalizacao = descricaoPersonalizacao;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public String getDescricaoPersonalizacao() {
        return descricaoPersonalizacao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    // Métodos modificadores

    public void alterarQuantidade(Integer quantidade) {
        validarQuantidade(quantidade);
        this.quantidade = quantidade;
    }

    public void alterarPrecoUnitario(BigDecimal precoUnitario) {
        validarPreco(precoUnitario);
        this.precoUnitario = precoUnitario;
    }

    public void alterarDescricaoPersonalizacao(String descricao) {
        this.descricaoPersonalizacao = descricao;
    }

    // Cálculo do subtotal

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(
                BigDecimal.valueOf(quantidade)
        );
    }

    // Validações

    private void validarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero."
            );
        }
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null ||
                preco.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "O preço deve ser maior que zero."
            );
        }
    }

    // Controle do relacionamento com Pedido

    void definirPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
