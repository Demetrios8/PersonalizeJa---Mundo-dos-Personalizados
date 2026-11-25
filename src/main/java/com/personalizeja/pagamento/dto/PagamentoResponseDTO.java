
package com.personalizeja.pagamento.dto;

import com.personalizeja.pagamento.enums.FormaPagamento;
import com.personalizeja.pagamento.enums.StatusPagamento;

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
