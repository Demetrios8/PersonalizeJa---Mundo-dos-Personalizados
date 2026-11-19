
package com.personalizeja.service;

import com.personalizeja.model.Material;
import com.personalizeja.model.MovimentacaoEstoque;
import com.personalizeja.model.Usuario;
import com.personalizeja.repository.MaterialRepository;
import com.personalizeja.repository.MovimentacaoEstoqueRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    public MaterialService(
            MaterialRepository materialRepository,
            MovimentacaoEstoqueRepository movimentacaoRepository) {

        this.materialRepository = materialRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public Material cadastrarMaterial(Material material) {
        if (material == null) {
            throw new IllegalArgumentException(
                    "O material não pode ser nulo."
            );
        }

        return materialRepository.save(material);
    }

    public List<Material> listarMateriais() {
        return materialRepository.findAll();
    }

    public Material buscarMaterialPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "O ID do material não pode ser nulo."
            );
        }

        return materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Material não encontrado com o ID: " + id
                ));
    }

    public Material atualizarMaterial(
            Long id,
            String nome,
            String descricao,
            String unidadeMedida) {

        Material material = buscarMaterialPorId(id);

        material.atualizarDados(nome, descricao, unidadeMedida);

        return materialRepository.save(material);
    }

    @Transactional
    public MovimentacaoEstoque registrarEntrada(
            Long materialId,
            BigDecimal quantidade,
            String motivo,
            Usuario usuario) {

        Material material = buscarMaterialPorId(materialId);

        MovimentacaoEstoque movimentacao =
                material.registrarEntrada(
                        quantidade,
                        motivo,
                        usuario
                );

        materialRepository.save(material);

        return movimentacaoRepository.save(movimentacao);
    }

    @Transactional
    public MovimentacaoEstoque registrarSaida(
            Long materialId,
            BigDecimal quantidade,
            String motivo,
            Usuario usuario) {

        Material material = buscarMaterialPorId(materialId);

        MovimentacaoEstoque movimentacao =
                material.registrarSaida(
                        quantidade,
                        motivo,
                        usuario
                );

        materialRepository.save(material);

        return movimentacaoRepository.save(movimentacao);
    }

    public List<Material> listarMateriaisAbaixoDoMinimo() {
        return materialRepository.findAll()
                .stream()
                .filter(Material::estaAbaixoDoMinimo)
                .toList();
    }
}
