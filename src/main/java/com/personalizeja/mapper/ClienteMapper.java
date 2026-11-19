package com.personalizeja.mapper;

import org.springframework.stereotype.Component;

import com.personalizeja.dto.ClienteRequestDTO;
import com.personalizeja.dto.ClienteResponseDTO;
import com.personalizeja.model.Cliente;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDTO dto) {
        return new Cliente(
                dto.nome(),
                dto.telefone(),
                dto.email(),
                dto.documento()
        );
    }

    public ClienteResponseDTO toResponse(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getDocumento(),
                cliente.getDataCadastro()
        );
    }
}