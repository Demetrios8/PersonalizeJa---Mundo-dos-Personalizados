
package com.personalizeja.mapper;

import com.personalizeja.dto.PagamentoResponseDTO;
import com.personalizeja.model.Pagamento;

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
