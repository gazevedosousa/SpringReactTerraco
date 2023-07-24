package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Long> {
    @Query("Select tp from TipoProduto tp where tp.dataExclusao is null order by tp.noTipoProduto asc")
    List<TipoProduto> findAllWhereDataExclusaoIsNull();
    boolean existsByNoTipoProdutoAndDataExclusaoIsNull(String noTipoProduto);
    Optional<TipoProduto> findByIdAndDataExclusaoIsNull(Long id);
}
