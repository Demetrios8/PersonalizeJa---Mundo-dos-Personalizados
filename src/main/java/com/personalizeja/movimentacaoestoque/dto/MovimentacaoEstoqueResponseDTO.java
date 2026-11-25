
package com.personalizeja.movimentacaoestoque.dto;

import com.personalizeja.movimentacaoestoque.enums.TipoMovimentacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentacaoEstoqueResponseDTO(

        Long id,
        TipoMovimentacao tipo,
        BigDecimal quantidade,
        LocalDateTime dataMovimentacao,
        String motivo,
        Long materialId,
        Long usuarioId

) {
}
