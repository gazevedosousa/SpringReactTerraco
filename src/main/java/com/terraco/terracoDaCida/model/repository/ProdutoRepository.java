package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("Select p from Produto p where p.dataExclusao is null")
    List<Produto> findAllWhereDataExclusaoIsNull();
    boolean existsByNoProdutoAndDataExclusaoIsNull(String noProduto);
    Optional<Produto> findByIdAndDataExclusaoIsNull(Long idProduto);
}
