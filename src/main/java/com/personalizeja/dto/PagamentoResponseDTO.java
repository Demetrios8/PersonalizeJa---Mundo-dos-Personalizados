
package com.personalizeja.dto;

import com.personalizeja.enums.FormaPagamento;
import com.personalizeja.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(

        Long id,
        BigDecimal valor,
        FormaPagamento formaPagamento,
        StatusPagamento statusPagamento,
        LocalDateTime dataCriacao,
        LocalDateTime dataPagamento,
        Long pedidoId

) {
}
