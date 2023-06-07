package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ComandaProdutoRepository extends JpaRepository<ComandaProduto, Long> {
    @Query("Select cp from ComandaProduto cp join cp.comanda c where c.id = :idComanda and cp.dataExclusao is null order by cp.dataCriacao asc")
    List<ComandaProduto> findByComandaIdAndDataExclusaoIsNull(Long idComanda);
    @Query("Select cp from ComandaProduto cp join cp.comanda c where DATE(c.dataCriacao) = :dataCriacao and cp.dataExclusao is null order by cp.dataCriacao asc")
    List<ComandaProduto> findByDataCriacaoAndDataExclusaoIsNull(LocalDate dataCriacao);
    @Query("Select cp from ComandaProduto cp join cp.comanda c where MONTH(c.dataCriacao) = :mes and YEAR(c.dataCriacao) = :ano and cp.dataExclusao is null order by cp.dataCriacao asc")
    List<ComandaProduto> findByMesCriacaoAndDataExclusaoIsNull(String mes, String ano);
    Optional<ComandaProduto> findByIdAndDataExclusaoIsNull(Long id);
}
