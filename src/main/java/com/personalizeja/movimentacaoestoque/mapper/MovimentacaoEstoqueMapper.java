
package com.personalizeja.movimentacaoestoque.mapper;

import com.personalizeja.movimentacaoestoque.dto.MovimentacaoEstoqueResponseDTO;
import com.personalizeja.movimentacaoestoque.entity.MovimentacaoEstoque;

import org.springframework.stereotype.Component;

@Component
public class MovimentacaoEstoqueMapper {

    public MovimentacaoEstoqueResponseDTO toResponse(
            MovimentacaoEstoque movimentacao) {

        return new MovimentacaoEstoqueResponseDTO(
                movimentacao.getId(),
                movimentacao.getTipo(),
                movimentacao.getQuantidade(),
                movimentacao.getDataMovimentacao(),
                movimentacao.getMotivo(),
                movimentacao.getMaterial().getId(),
                movimentacao.getUsuario().getId()
        );
    }
}
