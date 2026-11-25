package com.personalizeja.arte.dto;

import jakarta.validation.constraints.NotBlank;

public record ComentarioRequest(@NotBlank String comentario) {
}