package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
import com.terraco.terracoDaCida.exceptions.ErroComandaService;
import com.terraco.terracoDaCida.model.entity.Comanda;

import java.util.List;

public interface ComandaService {

    ComandaDTOView criar(Comanda comanda);
    ComandaDTOView deletar(Comanda comanda) throws ErroComandaService;
    ComandaDTOView alterarSituacao(Comanda comanda, String novaSituacaoComanda);
    Comanda buscarComanda(Long id);
    List<ComandaDTOView> buscarTodasAsComandas();
    void verificaCliente(String noCliente);

}
