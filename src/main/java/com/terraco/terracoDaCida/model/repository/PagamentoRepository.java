package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
