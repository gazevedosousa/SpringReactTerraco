package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    @Query("Select p from Pagamento p join p.comanda c where c.id = :idComanda and p.dataExclusao is null")
    List<Pagamento> findByComandaIdAndDataExclusaoIsNull(Long idComanda);
    Optional<Pagamento> findByIdAndDataExclusaoIsNull(Long id);
}
