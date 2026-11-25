package com.personalizeja.arte.dto;

import java.time.LocalDateTime;

import com.personalizeja.arte.enums.StatusAprovacao;
import com.personalizeja.arte.entity.Arte;

public record ArteResponse(
        Long id,
        Long produtoId,
        Integer versao,
        String arquivoUrl,
        StatusAprovacao status,
        String comentarioCliente,
        LocalDateTime dataEnvio,
        LocalDateTime dataAprovacao) {

    public static ArteResponse de(Arte a) {
        return new ArteResponse(a.getId(), a.getProduto().getId(), a.getVersao(), a.getArquivoUrl(),
                a.getStatus(), a.getComentarioCliente(), a.getDataEnvio(), a.getDataAprovacao());
    }
}