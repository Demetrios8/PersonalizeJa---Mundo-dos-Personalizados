package com.personalizeja.mapper;

import com.personalizeja.dto.ProdutoRequest;
import com.personalizeja.dto.ProdutoResponseDTO;
import com.personalizeja.model.Produto;

public class ProdutoMapper {

    private ProdutoMapper() {
    }

    public static Produto toEntity(ProdutoRequest dto) {
        if (dto == null) {
            return null;
        }
        return new Produto(
                dto.getNome(),       // Usando getNome()
                dto.getDescricao(),  // Usando getDescricao()
                dto.getPreco(),      // Usando getPreco()
                dto.getCodigoBarras() // Usando getCodigoBarras()
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