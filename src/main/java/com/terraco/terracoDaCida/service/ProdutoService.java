package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.Produto;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoService {

    ProdutoDTOView criar(Produto produto);
    ProdutoDTOView atualizar(Produto produto, BigDecimal novoVrProduto);
    ProdutoDTOView deletar(Produto produto);
    Produto buscarProduto(Long id);
    List<ProdutoDTOView> buscarTodosOsProdutos();
    void validarProduto(String noProduto);

}
