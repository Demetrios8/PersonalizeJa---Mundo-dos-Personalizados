
package com.personalizeja.pedido.dto;

import com.personalizeja.pedido.enums.CanalOrigem;
import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        CanalOrigem canalOrigem,
        List<ItemPedidoRequestDTO> itens
) {
}
