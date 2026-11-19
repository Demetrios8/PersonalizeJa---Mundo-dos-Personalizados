
package com.personalizeja.dto;

public record ItemPedidoRequestDTO(
        Long produtoId,
        Integer quantidade,
        String descricaoPersonalizacao
) {
}
