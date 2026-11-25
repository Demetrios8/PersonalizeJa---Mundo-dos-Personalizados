
package com.personalizeja.material.entity;

import com.personalizeja.movimentacaoestoque.entity.MovimentacaoEstoque;
import com.personalizeja.usuario.entity.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
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

    public Material(
            String nome,
            String descricao,
            String unidadeMedida,
            BigDecimal estoqueMinimo) {

        validarNome(nome);
        validarUnidadeMedida(unidadeMedida);
        validarEstoqueMinimo(estoqueMinimo);

        this.nome = nome.trim();
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida.trim();
        this.estoqueMinimo = estoqueMinimo;
        this.quantidadeEstoque = BigDecimal.ZERO;
    }

    // Atualização dos dados

    public void atualizarDados(
            String nome,
            String descricao,
            String unidadeMedida) {

        validarNome(nome);
        validarUnidadeMedida(unidadeMedida);

        this.nome = nome.trim();
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida.trim();
    }

    public void alterarEstoqueMinimo(BigDecimal novoEstoqueMinimo) {

        validarEstoqueMinimo(novoEstoqueMinimo);

        this.estoqueMinimo = novoEstoqueMinimo;
    }

    // Consultas de estoque

    public boolean estaAbaixoDoMinimo() {
        return quantidadeEstoque.compareTo(estoqueMinimo) < 0;
    }

    // Registro de movimentações

    public MovimentacaoEstoque registrarEntrada(
            BigDecimal quantidade,
            String motivo,
            Usuario usuario) {

        validarQuantidadeMovimentacao(quantidade);

        MovimentacaoEstoque movimentacao =
                MovimentacaoEstoque.entrada(
                        this, quantidade, motivo, usuario
                );

        this.quantidadeEstoque =
                this.quantidadeEstoque.add(quantidade);

        this.movimentacoes.add(movimentacao);

        return movimentacao;
    }

    public MovimentacaoEstoque registrarSaida(
            BigDecimal quantidade,
            String motivo,
            Usuario usuario) {

        validarQuantidadeMovimentacao(quantidade);

        if (this.quantidadeEstoque.compareTo(quantidade) < 0) {
            throw new IllegalStateException(
                    "Estoque insuficiente para realizar a saída."
            );
        }

        MovimentacaoEstoque movimentacao =
                MovimentacaoEstoque.saida(
                        this, quantidade, motivo, usuario
                );

        this.quantidadeEstoque =
                this.quantidadeEstoque.subtract(quantidade);

        this.movimentacoes.add(movimentacao);

        return movimentacao;
    }

    // Validações

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do material é obrigatório."
            );
        }
    }

    private void validarUnidadeMedida(String unidadeMedida) {
        if (unidadeMedida == null || unidadeMedida.isBlank()) {
            throw new IllegalArgumentException(
                    "A unidade de medida é obrigatória."
            );
        }
    }

    private void validarEstoqueMinimo(BigDecimal estoqueMinimo) {
        if (estoqueMinimo == null ||
                estoqueMinimo.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "O estoque mínimo não pode ser negativo."
            );
        }
    }

    private void validarQuantidadeMovimentacao(BigDecimal quantidade) {
        if (quantidade == null ||
                quantidade.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "A quantidade da movimentação deve ser maior que zero."
            );
        }
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public BigDecimal getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public List<MovimentacaoEstoque> getMovimentacoes() {
        return Collections.unmodifiableList(movimentacoes);
    }
}
