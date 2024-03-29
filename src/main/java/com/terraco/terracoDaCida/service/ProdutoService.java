package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.Produto;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoService {

    ProdutoDTOView criar(Produto produto);
    ProdutoDTOView atualizar(Produto produto, BigDecimal novoVrProduto, Long idTipoProduto);
    ProdutoDTOView deletar(Produto produto);
    Produto buscarProdutoNaoExcluido(Long id);
    List<ProdutoDTOView> buscarTodosOsProdutosNaoExcluidos();
    Produto buscarProduto(Long id);
    List<ProdutoDTOView> buscarTodosOsProdutos();
    List<ProdutoDTOView> buscarProdutosPorTipo(Long idTipoProduto);
    void validarProduto(String noProduto);

}
