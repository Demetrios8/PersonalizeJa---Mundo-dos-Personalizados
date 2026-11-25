
package com.personalizeja.pedido.dto;

import com.personalizeja.pedido.enums.StatusPedido;
import com.personalizeja.pedido.enums.CanalOrigem;

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
