package com.personalizeja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personalizeja.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Usuario> findByEmail(String email);
}