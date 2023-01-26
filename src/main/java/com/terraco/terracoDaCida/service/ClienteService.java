package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ClienteDTOView;
import com.terraco.terracoDaCida.model.entity.Cliente;

import java.util.List;

public interface ClienteService {

    ClienteDTOView criar(Cliente cliente);
    ClienteDTOView atualizar(Cliente cliente, String novoCelCliente, String novoEmailCliente);
    ClienteDTOView deletar(Cliente cliente);
    Cliente buscarCliente(Long id);
    List<ClienteDTOView> buscarTodosOsClientes();
    void validarCliente(String noCliente);

}
