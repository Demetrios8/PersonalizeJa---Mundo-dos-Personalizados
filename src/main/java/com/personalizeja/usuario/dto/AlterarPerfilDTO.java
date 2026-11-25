package com.personalizeja.usuario.dto;

import com.personalizeja.usuario.enums.PerfilUsuario;

import jakarta.validation.constraints.NotNull;

public record AlterarPerfilDTO(
        @NotNull(message = "O perfil é obrigatório.")
        PerfilUsuario perfil
) {
}