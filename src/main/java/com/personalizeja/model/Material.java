package com.personalizeja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "materiais")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadeEstoque;

    @Column(nullable = false)
    private String unidadeMedida;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal estoqueMinimo;

    @OneToMany(mappedBy = "material")
    private List<MovimentacaoEstoque> movimentacoes = new ArrayList<>();

    protected Material() {
    }

    public Material(String nome, String descricao, String unidadeMedida, BigDecimal estoqueMinimo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do material é obrigatório.");
        }
        if (unidadeMedida == null || unidadeMedida.isBlank()) {
            throw new IllegalArgumentException("A unidade de medida é obrigatória.");
        }
        if (estoqueMinimo == null || estoqueMinimo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O estoque mínimo não pode ser negativo.");
        }
        this.nome = nome;
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
        this.estoqueMinimo = estoqueMinimo;
        this.quantidadeEstoque = BigDecimal.ZERO;
    }

    public void atualizarDados(String nome, String descricao, String unidadeMedida) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do material é obrigatório.");
        }
        if (unidadeMedida == null || unidadeMedida.isBlank()) {
            throw new IllegalArgumentException("A unidade de medida é obrigatória.");
        }
        this.nome = nome;
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
    }

    public void alterarEstoqueMinimo(BigDecimal novoEstoqueMinimo) {
        if (novoEstoqueMinimo == null || novoEstoqueMinimo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O estoque mínimo não pode ser negativo.");
        }
        this.estoqueMinimo = novoEstoqueMinimo;
    }

    public boolean estaAbaixoDoMinimo() {
        return quantidadeEstoque.compareTo(estoqueMinimo) < 0;
    }

    public MovimentacaoEstoque registrarEntrada(BigDecimal quantidade, String motivo, Usuario usuario) {
        MovimentacaoEstoque movimentacao = MovimentacaoEstoque.entrada(this, quantidade, motivo, usuario);
        this.quantidadeEstoque = this.quantidadeEstoque.add(quantidade);
        this.movimentacoes.add(movimentacao);
        return movimentacao;
    }

    public MovimentacaoEstoque registrarSaida(BigDecimal quantidade, String motivo, Usuario usuario) {
        MovimentacaoEstoque movimentacao = MovimentacaoEstoque.saida(this, quantidade, motivo, usuario);
        if (this.quantidadeEstoque.compareTo(quantidade) < 0) {
            throw new IllegalStateException("Estoque insuficiente para realizar a saída.");
        }
        this.quantidadeEstoque = this.quantidadeEstoque.subtract(quantidade);
        this.movimentacoes.add(movimentacao);
        return movimentacao;
    }
}
