package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ClienteDTOView;
import com.terraco.terracoDaCida.model.entity.Cliente;

import java.util.List;

public interface ClienteService {

    ClienteDTOView criar(Cliente cliente);
    ClienteDTOView atualizar(Cliente cliente, String novoCelCliente, String novoEmailCliente);
    ClienteDTOView deletar(Cliente cliente);
    Cliente buscarClienteNaoExcluido(Long id);
    Cliente buscarCliente(Long id);
    Cliente buscarClientePorNome(String noCliente);
    List<ClienteDTOView> buscarTodosOsClientesNaoExcluidos();
    List<ClienteDTOView> buscarTodosOsClientes();
    void validarCliente(String noCliente);
    void validarDelecaoCliente(Long idCliente);

}
