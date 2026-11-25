
package com.personalizeja.pedido.entity;

import com.personalizeja.pedido.enums.StatusPedido;

import com.personalizeja.produto.entity.Produto;
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

        if (!produto.isAtivo()) {
            throw new IllegalArgumentException(
                    "Não é possível adicionar um produto desativado."
            );
        }

        validarQuantidade(quantidade);
        validarPreco(precoUnitario);
        validarDescricao(descricaoPersonalizacao);

        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.descricaoPersonalizacao = normalizarDescricao(
                descricaoPersonalizacao
        );
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
        verificarPedidoPendente();
        validarQuantidade(quantidade);
        this.quantidade = quantidade;
    }

    public void alterarPrecoUnitario(BigDecimal precoUnitario) {
        verificarPedidoPendente();
        validarPreco(precoUnitario);
        this.precoUnitario = precoUnitario;
    }

    public void alterarDescricaoPersonalizacao(String descricao) {
        verificarPedidoPendente();
        validarDescricao(descricao);

        this.descricaoPersonalizacao = normalizarDescricao(descricao);
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

    private void validarDescricao(String descricao) {
        if (descricao != null && descricao.length() > 500) {
            throw new IllegalArgumentException(
                    "A descrição da personalização deve ter no máximo 500 caracteres."
            );
        }
    }

    private String normalizarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            return null;
        }

        return descricao.trim();
    }

    private void verificarPedidoPendente() {
        if (pedido != null &&
                pedido.getStatus() != StatusPedido.PENDENTE) {

            throw new IllegalStateException(
                    "Só é possível alterar itens de pedidos pendentes."
            );
        }
    }

    // Controle do relacionamento com Pedido

    void definirPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
