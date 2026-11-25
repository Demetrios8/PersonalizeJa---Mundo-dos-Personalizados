
package com.personalizeja.pedido.dto;

public record ItemPedidoRequestDTO(
        Long produtoId,
        Integer quantidade,
        String descricaoPersonalizacao
) {
}
