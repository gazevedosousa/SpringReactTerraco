package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
