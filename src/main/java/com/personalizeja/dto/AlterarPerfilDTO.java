package com.personalizeja.dto;

import com.personalizeja.enums.PerfilUsuario;

import jakarta.validation.constraints.NotNull;

public record AlterarPerfilDTO(
        @NotNull(message = "O perfil é obrigatório.")
        PerfilUsuario perfil
) {
}