package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.TipoProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.TipoProduto;

import java.util.List;
import java.util.Optional;

public interface TipoProdutoService {

    TipoProdutoDTOView criar(TipoProduto produto);
    TipoProdutoDTOView deletar(TipoProduto produto);
    TipoProduto buscarTipoProdutoNaoExcluido(Long id);
    List<TipoProdutoDTOView> buscarTodosOsTiposProdutoNaoExcluidos();
    TipoProduto buscarTipoProduto(Long id);
    List<TipoProdutoDTOView> buscarTodosOsTiposProduto();
    void validarTipoProduto(String noTipoProduto);
    void validarDelecaoTipoProduto(TipoProduto tipoProduto);

}
