package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;

import java.util.List;

public interface ComandaProdutoService {

    ComandaProdutoDTOView criar(ComandaProduto comandaProduto);
    ComandaProdutoDTOView deletar(ComandaProduto comandaProduto);
    ComandaProduto buscarComandaProduto(Long id);
    List<ComandaProdutoDTOView> buscarProdutosDeUmaComanda(Long idComanda);
    void verificaSituacaoComanda (Comanda comanda);

}
