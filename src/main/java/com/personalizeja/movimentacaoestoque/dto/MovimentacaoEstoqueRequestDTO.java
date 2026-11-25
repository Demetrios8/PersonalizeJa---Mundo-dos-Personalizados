
package com.personalizeja.movimentacaoestoque.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MovimentacaoEstoqueRequestDTO(

        @NotNull(message = "A quantidade é obrigatória.")
        @DecimalMin(
                value = "0.001",
                message = "A quantidade deve ser maior que zero."
        )
        BigDecimal quantidade,

        @NotBlank(message = "O motivo é obrigatório.")
        String motivo,

        @NotNull(message = "O ID do usuário é obrigatório.")
        Long usuarioId

) {
}
