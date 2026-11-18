
package com.personalizeja.model;

import com.personalizeja.enums.TipoMovimentacao;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidade;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataMovimentacao;

    @Column(nullable = false, length = 500)
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    protected MovimentacaoEstoque() {
    }

    private MovimentacaoEstoque(
            Material material,
            TipoMovimentacao tipo,
            BigDecimal quantidade,
            String motivo,
            Usuario usuario) {

        validarMaterial(material);
        validarUsuario(usuario);
        validarTipo(tipo);
        validarQuantidade(quantidade);
        validarMotivo(motivo);

        this.material = material;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.motivo = motivo.trim();
        this.usuario = usuario;
    }

    @PrePersist
    private void aoCriar() {
        if (this.dataMovimentacao == null) {
            this.dataMovimentacao = LocalDateTime.now();
        }
    }

    // Métodos de criação

    static MovimentacaoEstoque entrada(
            Material material,
            BigDecimal quantidade,
            String motivo,
            Usuario usuario) {

        return new MovimentacaoEstoque(
                material,
                TipoMovimentacao.ENTRADA,
                quantidade,
                motivo,
                usuario
        );
    }

    static MovimentacaoEstoque saida(
            Material material,
            BigDecimal quantidade,
            String motivo,
            Usuario usuario) {

        return new MovimentacaoEstoque(
                material,
                TipoMovimentacao.SAIDA,
                quantidade,
                motivo,
                usuario
        );
    }

    // Validações

    private void validarMaterial(Material material) {
        if (material == null) {
            throw new IllegalArgumentException(
                    "O material é obrigatório."
            );
        }
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "O usuário é obrigatório."
            );
        }
    }

    private void validarTipo(TipoMovimentacao tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "O tipo da movimentação é obrigatório."
            );
        }
    }

    private void validarQuantidade(BigDecimal quantidade) {
        if (quantidade == null ||
                quantidade.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero."
            );
        }
    }

    private void validarMotivo(String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException(
                    "O motivo da movimentação é obrigatório."
            );
        }

        if (motivo.length() > 500) {
            throw new IllegalArgumentException(
                    "O motivo deve ter no máximo 500 caracteres."
            );
        }
    }

    // Getters

    public Long getId() {
        return id;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public String getMotivo() {
        return motivo;
    }

    public Material getMaterial() {
        return material;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
