
package com.personalizeja.mapper;

import com.personalizeja.dto.MovimentacaoEstoqueResponseDTO;
import com.personalizeja.model.MovimentacaoEstoque;

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
