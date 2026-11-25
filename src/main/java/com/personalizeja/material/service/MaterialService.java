
package com.personalizeja.material.service;

import com.personalizeja.material.entity.Material;
import com.personalizeja.movimentacaoestoque.entity.MovimentacaoEstoque;
import com.personalizeja.usuario.entity.Usuario;
import com.personalizeja.material.repository.MaterialRepository;
import com.personalizeja.movimentacaoestoque.repository.MovimentacaoEstoqueRepository;

import com.personalizeja.usuario.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;
    private final UsuarioService usuarioService;

    public MaterialService(
            MaterialRepository materialRepository,
            MovimentacaoEstoqueRepository movimentacaoRepository,
            UsuarioService usuarioService) {

        this.materialRepository = materialRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Material cadastrarMaterial(Material material) {

        if (material == null) {
            throw new IllegalArgumentException(
                    "O material não pode ser nulo."
            );
        }

        return materialRepository.save(material);
    }

    @Transactional(readOnly = true)
    public List<Material> listarMateriais() {
        return materialRepository.findAll();
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public Material atualizarMaterial(
            Long id,
            String nome,
            String descricao,
            String unidadeMedida,
            BigDecimal estoqueMinimo) {

        Material material = buscarMaterialPorId(id);

        material.atualizarDados(
                nome,
                descricao,
                unidadeMedida
        );

        material.alterarEstoqueMinimo(estoqueMinimo);

        return materialRepository.save(material);
    }

    @Transactional
    public MovimentacaoEstoque registrarEntrada(
            Long materialId,
            BigDecimal quantidade,
            String motivo,
            Long usuarioId) {

        Material material = buscarMaterialPorId(materialId);
        Usuario usuario = usuarioService.buscarEntidade(usuarioId);

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
            Long usuarioId) {

        Material material = buscarMaterialPorId(materialId);
        Usuario usuario = usuarioService.buscarEntidade(usuarioId);

        MovimentacaoEstoque movimentacao =
                material.registrarSaida(
                        quantidade,
                        motivo,
                        usuario
                );

        materialRepository.save(material);

        return movimentacaoRepository.save(movimentacao);
    }

    @Transactional(readOnly = true)
    public List<Material> listarMateriaisAbaixoDoMinimo() {

        return materialRepository.findAll()
                .stream()
                .filter(Material::estaAbaixoDoMinimo)
                .toList();
    }
}
