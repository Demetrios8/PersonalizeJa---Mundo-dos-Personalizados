package com.personalizeja.usuario.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personalizeja.usuario.dto.AlterarPerfilDTO;
import com.personalizeja.usuario.dto.AlterarSenhaDTO;
import com.personalizeja.usuario.dto.UsuarioAtualizacaoDTO;
import com.personalizeja.usuario.dto.UsuarioRequestDTO;
import com.personalizeja.usuario.dto.UsuarioResponseDTO;
import com.personalizeja.usuario.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO criado = service.criar(dto);
        URI local = URI.create("/api/usuarios/" + criado.id());
        return ResponseEntity.created(local).body(criado);
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar(@PathVariable Long id,
                                        @Valid @RequestBody UsuarioAtualizacaoDTO dto) {
        return service.atualizar(id, dto);
    }

    @PatchMapping("/{id}/senha")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id,
                                             @Valid @RequestBody AlterarSenhaDTO dto) {
        service.alterarSenha(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/perfil")
    public UsuarioResponseDTO alterarPerfil(@PathVariable Long id,
                                            @Valid @RequestBody AlterarPerfilDTO dto) {
        return service.alterarPerfil(id, dto);
    }

    @PatchMapping("/{id}/ativar")
    public UsuarioResponseDTO ativar(@PathVariable Long id) {
        return service.ativar(id);
    }

    @PatchMapping("/{id}/desativar")
    public UsuarioResponseDTO desativar(@PathVariable Long id) {
        return service.desativar(id);
    }
}