
package com.personalizeja.material.controller;

import com.personalizeja.material.dto.MaterialRequestDTO;
import com.personalizeja.material.dto.MaterialResponseDTO;
import com.personalizeja.movimentacaoestoque.dto.MovimentacaoEstoqueRequestDTO;
import com.personalizeja.movimentacaoestoque.dto.MovimentacaoEstoqueResponseDTO;

import com.personalizeja.material.mapper.MaterialMapper;
import com.personalizeja.movimentacaoestoque.mapper.MovimentacaoEstoqueMapper;

import com.personalizeja.material.entity.Material;
import com.personalizeja.movimentacaoestoque.entity.MovimentacaoEstoque;
import com.personalizeja.material.service.MaterialService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/materiais")
public class MaterialController {

    private final MaterialService materialService;
    private final MaterialMapper materialMapper;
    private final MovimentacaoEstoqueMapper movimentacaoMapper;

    public MaterialController(
            MaterialService materialService,
            MaterialMapper materialMapper,
            MovimentacaoEstoqueMapper movimentacaoMapper) {

        this.materialService = materialService;
        this.materialMapper = materialMapper;
        this.movimentacaoMapper = movimentacaoMapper;
    }

    // Cadastrar material
    @PostMapping
    public ResponseEntity<MaterialResponseDTO> cadastrar(
            @Valid @RequestBody MaterialRequestDTO dto) {

        Material material = materialMapper.toEntity(dto);
        Material cadastrado = materialService.cadastrarMaterial(material);

        URI local = URI.create("/api/materiais/" + cadastrado.getId());

        return ResponseEntity
                .created(local)
                .body(materialMapper.toResponse(cadastrado));
    }

    // Listar todos os materiais
    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> listarTodos() {

        List<MaterialResponseDTO> materiais =
                materialService.listarMateriais()
                        .stream()
                        .map(materialMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(materiais);
    }

    // Buscar material por ID
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> buscarPorId(
            @PathVariable Long id) {

        Material material = materialService.buscarMaterialPorId(id);

        return ResponseEntity.ok(
                materialMapper.toResponse(material)
        );
    }

    // Atualizar dados do material
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MaterialRequestDTO dto) {

        Material atualizado = materialService.atualizarMaterial(
                id,
                dto.nome(),
                dto.descricao(),
                dto.unidadeMedida(),
                dto.estoqueMinimo()
        );

        return ResponseEntity.ok(
                materialMapper.toResponse(atualizado)
        );
    }

    // Listar materiais abaixo do estoque minimo
    @GetMapping("/abaixo-do-minimo")
    public ResponseEntity<List<MaterialResponseDTO>> listarAbaixoDoMinimo() {

        List<MaterialResponseDTO> materiais =
                materialService.listarMateriaisAbaixoDoMinimo()
                        .stream()
                        .map(materialMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(materiais);
    }

    // Registrar entrada de estoque
    @PostMapping("/{id}/entradas")
    public ResponseEntity<MovimentacaoEstoqueResponseDTO> registrarEntrada(
            @PathVariable Long id,
            @Valid @RequestBody MovimentacaoEstoqueRequestDTO dto) {

        MovimentacaoEstoque movimentacao =
                materialService.registrarEntrada(
                        id,
                        dto.quantidade(),
                        dto.motivo(),
                        dto.usuarioId()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movimentacaoMapper.toResponse(movimentacao));
    }

    // Registrar saida de estoque
    @PostMapping("/{id}/saidas")
    public ResponseEntity<MovimentacaoEstoqueResponseDTO> registrarSaida(
            @PathVariable Long id,
            @Valid @RequestBody MovimentacaoEstoqueRequestDTO dto) {

        MovimentacaoEstoque movimentacao =
                materialService.registrarSaida(
                        id,
                        dto.quantidade(),
                        dto.motivo(),
                        dto.usuarioId()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movimentacaoMapper.toResponse(movimentacao));
    }

    // Tratar erros de argumentos invalidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarArgumentoInvalido(
            IllegalArgumentException ex) {

        String mensagem = ex.getMessage();

        boolean naoEncontrado = mensagem != null
                && mensagem.contains("não encontrado");

        HttpStatus status = naoEncontrado
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(mensagem);
    }

    // Tratar erros de regras de negocio
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> tratarEstadoInvalido(
            IllegalStateException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}
