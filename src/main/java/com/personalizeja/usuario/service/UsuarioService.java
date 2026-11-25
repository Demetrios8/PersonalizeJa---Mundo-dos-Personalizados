package com.personalizeja.usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.personalizeja.usuario.dto.AlterarPerfilDTO;
import com.personalizeja.usuario.dto.AlterarSenhaDTO;
import com.personalizeja.usuario.dto.UsuarioAtualizacaoDTO;
import com.personalizeja.usuario.dto.UsuarioRequestDTO;
import com.personalizeja.usuario.dto.UsuarioResponseDTO;
import com.personalizeja.exception.RecursoNaoEncontradoException;
import com.personalizeja.exception.RegraNegocioException;
import com.personalizeja.usuario.mapper.UsuarioMapper;
import com.personalizeja.usuario.entity.Usuario;
import com.personalizeja.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        String email = normalizarEmail(dto.email());

        if (repository.existsByEmail(email)) {
            throw new RegraNegocioException("Já existe um usuário com este e-mail.");
        }

        UsuarioRequestDTO dados = new UsuarioRequestDTO(
                dto.nome().trim(), email, dto.senha(), dto.perfil());

        Usuario usuario = repository.save(mapper.toEntity(dados));
        return mapper.toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioAtualizacaoDTO dto) {
        Usuario usuario = buscarEntidade(id);
        String email = normalizarEmail(dto.email());

        if (repository.existsByEmailAndIdNot(email, id)) {
            throw new RegraNegocioException("Já existe um usuário com este e-mail.");
        }

        usuario.atualizarDados(dto.nome().trim(), email);
        return mapper.toResponse(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, AlterarSenhaDTO dto) {
        buscarEntidade(id).alterarSenha(dto.novaSenha());
    }

    @Transactional
    public UsuarioResponseDTO alterarPerfil(Long id, AlterarPerfilDTO dto) {
        Usuario usuario = buscarEntidade(id);
        usuario.alterarPerfil(dto.perfil());
        return mapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO ativar(Long id) {
        Usuario usuario = buscarEntidade(id);
        usuario.ativar();
        return mapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO desativar(Long id) {
        Usuario usuario = buscarEntidade(id);
        usuario.desativar();
        return mapper.toResponse(usuario);
    }

    // Metodo auxiliar usado por outros services (ex.: MaterialService)
    @Transactional(readOnly = true)
    public Usuario buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado: " + id));
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }
}