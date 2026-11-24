package com.personalizeja.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personalizeja.model.Arte;

public interface ArteRepository extends JpaRepository<Arte, Long> {

    List<Arte> findByProdutoIdOrderByVersaoDesc(Long produtoId);

    Optional<Arte> findFirstByProdutoIdOrderByVersaoDesc(Long produtoId);
}