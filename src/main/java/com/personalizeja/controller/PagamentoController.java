
package com.personalizeja.controller;

import com.personalizeja.dto.PagamentoRequestDTO;
import com.personalizeja.dto.PagamentoAtualizacaoDTO;
import com.personalizeja.dto.PagamentoResponseDTO;
import com.personalizeja.mapper.PagamentoMapper;
import com.personalizeja.model.Pagamento;
import com.personalizeja.service.PagamentoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // Cadastrar pagamento
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criar(
            @Valid @RequestBody PagamentoRequestDTO dto) {

        Pagamento pagamento = pagamentoService.criar(
                dto.valor(),
                dto.formaPagamento(),
                dto.pedidoId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PagamentoMapper.toResponseDTO(pagamento));
    }

    // Listar todos os pagamentos
    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> listarTodos() {

        List<PagamentoResponseDTO> pagamentos =
                pagamentoService.listarTodos()
                        .stream()
                        .map(PagamentoMapper::toResponseDTO)
                        .toList();

        return ResponseEntity.ok(pagamentos);
    }

    // Buscar pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        Pagamento pagamento = pagamentoService.buscarPorId(id);

        return ResponseEntity.ok(
                PagamentoMapper.toResponseDTO(pagamento)
        );
    }

    // Atualizar pagamento
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PagamentoAtualizacaoDTO dto) {

        Pagamento pagamento = pagamentoService.atualizar(
                id,
                dto.valor(),
                dto.formaPagamento()
        );

        return ResponseEntity.ok(
                PagamentoMapper.toResponseDTO(pagamento)
        );
    }

    // Aprovar pagamento
    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<PagamentoResponseDTO> aprovar(
            @PathVariable Long id) {

        Pagamento pagamento = pagamentoService.aprovar(id);

        return ResponseEntity.ok(
                PagamentoMapper.toResponseDTO(pagamento)
        );
    }

    // Recusar pagamento
    @PatchMapping("/{id}/recusar")
    public ResponseEntity<PagamentoResponseDTO> recusar(
            @PathVariable Long id) {

        Pagamento pagamento = pagamentoService.recusar(id);

        return ResponseEntity.ok(
                PagamentoMapper.toResponseDTO(pagamento)
        );
    }

    // Cancelar pagamento
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PagamentoResponseDTO> cancelar(
            @PathVariable Long id) {

        Pagamento pagamento = pagamentoService.cancelar(id);

        return ResponseEntity.ok(
                PagamentoMapper.toResponseDTO(pagamento)
        );
    }

    // Estornar pagamento
    @PatchMapping("/{id}/estornar")
    public ResponseEntity<PagamentoResponseDTO> estornar(
            @PathVariable Long id) {

        Pagamento pagamento = pagamentoService.estornar(id);

        return ResponseEntity.ok(
                PagamentoMapper.toResponseDTO(pagamento)
        );
    }

    // Tratamento de erros de validação e regras de negócio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarArgumentoInvalido(
            IllegalArgumentException ex) {

        String mensagem = ex.getMessage();

        boolean naoEncontrado = mensagem != null &&
                mensagem.contains("não encontrado");

        HttpStatus status = naoEncontrado
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(mensagem);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> tratarEstadoInvalido(
            IllegalStateException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}
