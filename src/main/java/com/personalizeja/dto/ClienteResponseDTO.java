package com.personalizeja.dto;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String telefone,
        String email,
        String documento,
        LocalDateTime dataCadastro
) {
}