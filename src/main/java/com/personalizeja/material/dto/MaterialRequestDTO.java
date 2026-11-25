
package com.personalizeja.material.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MaterialRequestDTO(

        @NotBlank(message = "O nome do material é obrigatório.")
        String nome,

        String descricao,

        @NotBlank(message = "A unidade de medida é obrigatória.")
        String unidadeMedida,

        @NotNull(message = "O estoque mínimo é obrigatório.")
        @DecimalMin(
                value = "0.0",
                message = "O estoque mínimo não pode ser negativo."
        )
        BigDecimal estoqueMinimo

) {
}
