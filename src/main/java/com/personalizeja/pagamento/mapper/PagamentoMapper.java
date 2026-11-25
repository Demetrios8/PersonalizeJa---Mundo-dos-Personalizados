
package com.personalizeja.pagamento.mapper;

import com.personalizeja.pagamento.dto.PagamentoResponseDTO;
import com.personalizeja.pagamento.entity.Pagamento;

public class PagamentoMapper {

    private PagamentoMapper() {
    }

    public static PagamentoResponseDTO toResponseDTO(
            Pagamento pagamento) {

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getFormaPagamento(),
                pagamento.getStatusPagamento(),
                pagamento.getDataCriacao(),
                pagamento.getDataPagamento(),
                pagamento.getPedido().getId()
        );
    }
}
