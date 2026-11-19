package com.personalizeja.dto;

import jakarta.validation.constraints.NotBlank;

public record ComentarioRequest(@NotBlank String comentario) {
}''