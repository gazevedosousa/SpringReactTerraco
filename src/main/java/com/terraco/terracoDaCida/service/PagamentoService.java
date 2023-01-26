package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Pagamento;


public interface PagamentoService {

    PagamentoDTOView criar(Pagamento pagamento);
    PagamentoDTOView deletar(Pagamento pagamento);
    Pagamento buscarPagamento(Long id);

    void validarPagamento(Comanda comanda);
}
