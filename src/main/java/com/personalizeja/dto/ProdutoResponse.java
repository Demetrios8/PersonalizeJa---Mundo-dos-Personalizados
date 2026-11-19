package com.personalizeja.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.personalizeja.model.Produto;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        String codigoBarras,
        LocalDateTime dataFabricacao,
        boolean ativo) {

    public static ProdutoResponse de(Produto p) {
        return new ProdutoResponse(p.getId(), p.getNome(), p.getDescricao(), p.getPreco(),
                p.getCodigoBarras(), p.getDataFabricacao(), p.isAtivo());
    }
}