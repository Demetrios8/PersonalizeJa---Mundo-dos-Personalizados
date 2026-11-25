
package com.personalizeja.movimentacaoestoque.repository;

import com.personalizeja.movimentacaoestoque.entity.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoEstoqueRepository
        extends JpaRepository<MovimentacaoEstoque, Long> {

}
