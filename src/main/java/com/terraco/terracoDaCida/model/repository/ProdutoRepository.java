package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("Select p from Produto p join p.tipoProduto tp where p.dataExclusao is null order by  tp.noTipoProduto asc, p.noProduto asc")
    List<Produto> findAllWhereDataExclusaoIsNull();
    @Query("Select p from Produto p join p.tipoProduto tp where tp.id = :idTipoProduto and p.dataExclusao is null order by p.noProduto asc")
    List<Produto> findAllWhereTipoProdutoAndDataExclusaoIsNull(Long idTipoProduto);
    boolean existsByNoProdutoAndDataExclusaoIsNull(String noProduto);
    Optional<Produto> findByIdAndDataExclusaoIsNull(Long idProduto);
}
