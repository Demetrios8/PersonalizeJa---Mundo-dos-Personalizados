
package com.personalizeja.dto;

import com.personalizeja.enums.StatusPedido;
import com.personalizeja.enums.CanalOrigem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        LocalDateTime dataPedido,
        StatusPedido status,
        CanalOrigem canalOrigem,
        Long clienteId,
        String clienteNome,
        List<ItemPedidoResponseDTO> itens,
        BigDecimal valorTotal
) {
}
