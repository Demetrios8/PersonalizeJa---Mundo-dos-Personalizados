package com.personalizeja.usuario.dto;

import com.personalizeja.usuario.enums.PerfilUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        PerfilUsuario perfil,
        boolean ativo
) {
}