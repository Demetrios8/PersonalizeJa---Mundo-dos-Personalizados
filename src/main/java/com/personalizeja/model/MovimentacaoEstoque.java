package com.personalizeja.model;

import com.personalizeja.enums.TipoMovimentacao;
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
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
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

    private MovimentacaoEstoque(Material material, TipoMovimentacao tipo,
                               BigDecimal quantidade, String motivo, Usuario usuario) {
        if (material == null) {
            throw new IllegalArgumentException("O material é obrigatório.");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário é obrigatório.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo da movimentação é obrigatório.");
        }
        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("O motivo da movimentação é obrigatório.");
        }
        this.material = material;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.usuario = usuario;
        this.dataMovimentacao = LocalDateTime.now();
    }

    static MovimentacaoEstoque entrada(Material material, BigDecimal quantidade,
                                      String motivo, Usuario usuario) {
        return new MovimentacaoEstoque(material, TipoMovimentacao.ENTRADA,
                quantidade, motivo, usuario);
    }

    static MovimentacaoEstoque saida(Material material, BigDecimal quantidade,
                                    String motivo, Usuario usuario) {
        return new MovimentacaoEstoque(material, TipoMovimentacao.SAIDA,
                quantidade, motivo, usuario);
    }
}
