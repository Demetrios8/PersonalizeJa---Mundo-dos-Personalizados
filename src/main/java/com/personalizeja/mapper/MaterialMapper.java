
package com.personalizeja.mapper;

import com.personalizeja.dto.MaterialRequestDTO;
import com.personalizeja.dto.MaterialResponseDTO;
import com.personalizeja.model.Material;

import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {

    public Material toEntity(MaterialRequestDTO dto) {
        return new Material(
                dto.nome(),
                dto.descricao(),
                dto.unidadeMedida(),
                dto.estoqueMinimo()
        );
    }

    public MaterialResponseDTO toResponse(Material material) {
        return new MaterialResponseDTO(
                material.getId(),
                material.getNome(),
                material.getDescricao(),
                material.getQuantidadeEstoque(),
                material.getUnidadeMedida(),
                material.getEstoqueMinimo(),
                material.estaAbaixoDoMinimo()
        );
    }
}
