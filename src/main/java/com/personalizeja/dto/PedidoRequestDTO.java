
package com.personalizeja.dto;

import com.personalizeja.enums.CanalOrigem;
import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        CanalOrigem canalOrigem,
        List<ItemPedidoRequestDTO> itens
) {
}
