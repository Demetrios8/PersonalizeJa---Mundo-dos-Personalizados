package com.personalizeja.produto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.personalizeja.arte.dto.ComentarioRequest;
import com.personalizeja.arte.dto.ArteResponse;
import com.personalizeja.arte.dto.ArteRequest;
import com.personalizeja.produto.dto.ProdutoRequest;
import com.personalizeja.produto.dto.ProdutoResponse;
import com.personalizeja.produto.entity.Produto;
import com.personalizeja.produto.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/produtos")
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoRequest req) {
        Produto salvo = service.cadastrar(paraEntidade(req));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponse.de(salvo));
    }

    @GetMapping("/produtos")
    public List<ProdutoResponse> listar() {
        return service.listar().stream().map(ProdutoResponse::de).toList();
    }

    @GetMapping("/produtos/{id}")
    public ProdutoResponse buscar(@PathVariable Long id) {
        return ProdutoResponse.de(service.buscarPorId(id));
    }

    @PutMapping("/produtos/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest req) {
        return ProdutoResponse.de(service.atualizar(id, paraEntidade(req)));
    }

    @PatchMapping("/produtos/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/produtos/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/produtos/{id}/artes")
    public ResponseEntity<ArteResponse> enviarArte(@PathVariable Long id, @Valid @RequestBody ArteRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ArteResponse.de(service.enviarArte(id, req.arquivoUrl())));
    }

    @GetMapping("/produtos/{id}/artes")
    public List<ArteResponse> listarArtes(@PathVariable Long id) {
        return service.listarArtes(id).stream().map(ArteResponse::de).toList();
    }

    @PatchMapping("/artes/{id}/aprovar")
    public ArteResponse aprovar(@PathVariable Long id) {
        return ArteResponse.de(service.aprovarArte(id));
    }

    @PatchMapping("/artes/{id}/reprovar")
    public ArteResponse reprovar(@PathVariable Long id, @Valid @RequestBody ComentarioRequest req) {
        return ArteResponse.de(service.reprovarArte(id, req.comentario()));
    }

    @PatchMapping("/artes/{id}/revisao")
    public ArteResponse solicitarRevisao(@PathVariable Long id, @Valid @RequestBody ComentarioRequest req) {
        return ArteResponse.de(service.solicitarRevisao(id, req.comentario()));
    }

    private Produto paraEntidade(ProdutoRequest r) {
        return new Produto(r.getNome(), r.getDescricao(), r.getPreco(), r.getCodigoBarras());
    }
}