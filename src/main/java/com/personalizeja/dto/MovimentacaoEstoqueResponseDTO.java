
package com.personalizeja.dto;

import com.personalizeja.enums.TipoMovimentacao;

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
