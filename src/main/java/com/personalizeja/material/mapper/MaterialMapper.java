
package com.personalizeja.material.mapper;

import com.personalizeja.material.dto.MaterialRequestDTO;
import com.personalizeja.material.dto.MaterialResponseDTO;
import com.personalizeja.material.entity.Material;

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
