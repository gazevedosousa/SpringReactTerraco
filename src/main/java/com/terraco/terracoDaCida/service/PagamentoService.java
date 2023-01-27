package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Pagamento;

import java.util.List;


public interface PagamentoService {

    PagamentoDTOView criar(Pagamento pagamento);
    PagamentoDTOView deletar(Pagamento pagamento);
    Pagamento buscarPagamento(Long id);
    List<PagamentoDTOView> buscarPagamentosDeUmaComanda(Long idComanda);
    void validarPagamento(Comanda comanda);
}
