package com.personalizeja.dto;

import jakarta.validation.constraints.NotBlank;

public record ArteRequest(@NotBlank String arquivoUrl) {
}