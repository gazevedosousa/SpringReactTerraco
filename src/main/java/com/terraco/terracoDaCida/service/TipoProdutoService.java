package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.TipoProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.TipoProduto;

import java.util.List;

public interface TipoProdutoService {

    TipoProdutoDTOView criar(TipoProduto produto);
    TipoProdutoDTOView deletar(TipoProduto produto);
    TipoProduto buscarTipoProduto(Long id);
    List<TipoProdutoDTOView> buscarTodosOsTiposProduto();
    void validarTipoProduto(String noTipoProduto);

}
