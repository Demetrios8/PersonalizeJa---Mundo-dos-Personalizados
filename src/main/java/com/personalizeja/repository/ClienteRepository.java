package com.personalizeja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personalizeja.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByDocumento(String documento);

    boolean existsByDocumentoAndIdNot(String documento, Long id);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}