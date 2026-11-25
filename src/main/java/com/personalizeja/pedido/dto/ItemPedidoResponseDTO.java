
package com.personalizeja.pedido.dto;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long id,
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        BigDecimal precoUnitario,
        String descricaoPersonalizacao,
        BigDecimal subtotal
) {
}
