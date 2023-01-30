package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
import com.terraco.terracoDaCida.model.entity.Comanda;

import java.util.List;

public interface ComandaService {

    ComandaDTOView criar(Comanda comanda);
    ComandaDTOView deletar(Comanda comanda);
    ComandaDTOView fecharComanda(Comanda comanda);
    ComandaDTOView reabrirComanda(Comanda comanda);
    Comanda buscarComandaNaoExcluida(Long id);
    Comanda buscarComanda(Long id);
    List<ComandaDTOView> buscarTodasAsComandasNaoExcluidas();
    List<ComandaDTOView> buscarTodasAsComandas();
    List<ComandaDTOView> buscarComandasPorCliente(Long idCliente);
    List<ComandaDTOView> buscarComandasAbertasPorCliente(Long idCliente);
    void validaDelecaoComanda(Comanda comanda);

}
