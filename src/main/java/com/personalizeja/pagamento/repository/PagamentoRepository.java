
package com.personalizeja.pagamento.repository;

import com.personalizeja.pagamento.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository
        extends JpaRepository<Pagamento, Long> {
}
