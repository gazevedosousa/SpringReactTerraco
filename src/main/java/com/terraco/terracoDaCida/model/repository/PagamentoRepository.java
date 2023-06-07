package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    @Query("Select p from Pagamento p join p.comanda c where c.id = :idComanda and p.dataExclusao is null order by p.dataCriacao desc")
    List<Pagamento> findByComandaIdAndDataExclusaoIsNull(Long idComanda);
    Optional<Pagamento> findByIdAndDataExclusaoIsNull(Long id);
    @Query("Select p from Pagamento p join p.comanda c where DATE(c.dataCriacao) = :dataCriacao and p.dataExclusao is null order by p.dataCriacao asc")
    List<Pagamento> findByDataCriacaoAndDataExclusaoIsNull(LocalDate dataCriacao);
    @Query("Select p from Pagamento p join p.comanda c where MONTH(c.dataCriacao) = :mes and YEAR(c.dataCriacao) = :ano and p.dataExclusao is null order by p.dataCriacao asc")
    List<Pagamento> findByMesCriacaoAndDataExclusaoIsNull(String mes, String ano);
    @Query("Select p from Pagamento p join p.comanda c join c.cliente cl where cl.id = :id and p.dataExclusao is null order by p.dataCriacao asc")
    List<Pagamento> findByIdClienteAndDataExclusaoIsNull(Long id);
}
