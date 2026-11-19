
package com.personalizeja.dto;

import java.math.BigDecimal;

public record MaterialResponseDTO(

        Long id,
        String nome,
        String descricao,
        BigDecimal quantidadeEstoque,
        String unidadeMedida,
        BigDecimal estoqueMinimo,
        boolean abaixoDoMinimo

) {
}
