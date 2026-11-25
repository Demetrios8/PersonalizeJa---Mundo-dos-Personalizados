package com.personalizeja.arte.dto;

import jakarta.validation.constraints.NotBlank;

public record ArteRequest(@NotBlank String arquivoUrl) {
}