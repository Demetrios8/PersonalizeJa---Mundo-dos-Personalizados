
package com.personalizeja.pedido.controller;

import com.personalizeja.pedido.dto.PedidoRequestDTO;
import com.personalizeja.pedido.dto.PedidoResponseDTO;
import com.personalizeja.pedido.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Cadastrar pedido

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> cadastrar(
            @RequestBody PedidoRequestDTO dto) {

        PedidoResponseDTO pedido = pedidoService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedido);
    }

    // Listar todos os pedidos

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {

        return ResponseEntity.ok(
                pedidoService.listarTodos()
        );
    }

    // Buscar pedido por ID

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.buscarPorId(id)
        );
    }

    // Iniciar produção

    @PatchMapping("/{id}/iniciar-producao")
    public ResponseEntity<PedidoResponseDTO> iniciarProducao(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.iniciarProducao(id)
        );
    }

    // Concluir pedido

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<PedidoResponseDTO> concluir(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.concluir(id)
        );
    }

    // Entregar pedido

    @PatchMapping("/{id}/entregar")
    public ResponseEntity<PedidoResponseDTO> entregar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.entregar(id)
        );
    }

    // Cancelar pedido

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponseDTO> cancelar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.cancelar(id)
        );
    }
}
