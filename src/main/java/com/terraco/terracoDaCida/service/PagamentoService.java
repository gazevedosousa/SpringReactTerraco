package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Pagamento;

import java.util.List;


public interface PagamentoService {

    PagamentoDTOView pagarParcial(Pagamento pagamento);
    PagamentoDTOView pagarTotal(Pagamento pagamento);
    PagamentoDTOView estornarPagamento(Pagamento pagamento);
    Pagamento buscarPagamento(Long id);
    List<PagamentoDTOView> buscarPagamentosDeUmaComanda(Long idComanda);
    List<PagamentoDTOView> buscarPagamentosEmUmaData(String data);
    List<PagamentoDTOView> buscarPagamentosEmUmMes(String data);
    List<PagamentoDTOView> buscarPagamentosDeUmCliente(Long idCliente);
    void validarPagamento(Comanda comanda);
}
