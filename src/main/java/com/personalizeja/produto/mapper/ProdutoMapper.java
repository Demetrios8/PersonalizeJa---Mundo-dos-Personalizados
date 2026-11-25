package com.personalizeja.produto.mapper;

import com.personalizeja.produto.dto.ProdutoRequest;
import com.personalizeja.produto.dto.ProdutoResponse; // Ajuste para o nome real do seu DTO de resposta
import com.personalizeja.produto.entity.Produto;

public class ProdutoMapper {

    private ProdutoMapper() {
    }

    public static Produto toEntity(ProdutoRequest dto) {
        if (dto == null) {
            return null;
        }
        return new Produto(
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                dto.getCodigoBarras()
        );
    }

    public static ProdutoResponse toResponseDTO(Produto produto) {
        if (produto == null) {
            return null;
        }

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getCodigoBarras(),
                produto.getDataFabricacao(),
                produto.isAtivo()
        );
    }
}