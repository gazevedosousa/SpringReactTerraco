package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComandaProdutoRepository extends JpaRepository<ComandaProduto, Long> {
    @Query("Select cp from ComandaProduto cp join cp.comanda c where c.id = :idComanda and cp.dataExclusao is null")
    List<ComandaProduto> findByComandaIdAndDataExclusaoIsNull(Long idComanda);

    Optional<ComandaProduto> findByIdAndDataExclusaoIsNull(Long id);
}
