package com.personalizeja.usuario.mapper;

import org.springframework.stereotype.Component;

import com.personalizeja.usuario.dto.UsuarioRequestDTO;
import com.personalizeja.usuario.dto.UsuarioResponseDTO;
import com.personalizeja.usuario.entity.Usuario;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        return new Usuario(
                dto.nome(),
                dto.email(),
                dto.senha(),
                dto.perfil()
        );
    }

    // A senha nunca é devolvida na resposta
    public UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.isAtivo()
        );
    }
}