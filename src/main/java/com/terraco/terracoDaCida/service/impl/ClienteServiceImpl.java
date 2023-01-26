package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.ClienteDTOView;
import com.terraco.terracoDaCida.exceptions.ErroClienteService;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.repository.ClienteRepository;
import com.terraco.terracoDaCida.service.ClienteService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper = ClienteMapper.INSTANCE;


    @Override
    @Transactional
    public ClienteDTOView criar(Cliente cliente) {
        validarCliente(cliente.getNoCliente());
        cliente.setDataCriacao(LocalDateTime.now());
        cliente.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(cliente));
    }

    @Override
    @Transactional
    public ClienteDTOView atualizar(Cliente cliente, String novoCelCliente, String novoEmailCliente) {
        Cliente clienteAtualizado = repository.findByIdAndDataExclusaoIsNull(cliente.getId())
                .orElseThrow(() -> new ErroClienteService("Cliente não encontrado no Banco de Dados"));

        if(novoCelCliente != null){
            clienteAtualizado.setCelCliente(novoCelCliente);
        }
        if(novoEmailCliente != null){
            clienteAtualizado.setEmailCliente(novoEmailCliente);
        }

        clienteAtualizado.setDataAtualizacao(LocalDateTime.now());


        return mapper.toDto(repository.save(clienteAtualizado));
    }

    @Override
    @Transactional
    public ClienteDTOView deletar(Cliente cliente) {
        Cliente clienteDeletado = repository.findByIdAndDataExclusaoIsNull(cliente.getId())
                .orElseThrow(() -> new ErroClienteService("Cliente não encontrado no Banco de Dados"));

        clienteDeletado.setDataExclusao(LocalDateTime.now());
        clienteDeletado.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(clienteDeletado));
    }

    @Override
    public Cliente buscarCliente(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ErroClienteService("Cliente não encontrado no Banco de Dados"));
    }

    @Override
    public List<ClienteDTOView> buscarTodosOsClientes() {
        List<Cliente> clientes = repository.findAllWhereDataExclusaoIsNull();
        List<ClienteDTOView> clienteDTOS = new ArrayList<>();

        clientes.forEach(cliente -> {
            clienteDTOS.add(mapper.toDto(cliente));
        });

        return clienteDTOS;
    }

    @Override
    public void validarCliente(String noCliente) {
        boolean existe = repository.existsByNoClienteAndDataExclusaoIsNull(noCliente);

        if(existe){
            throw new ErroClienteService("Cliente com o mesmo nome já existe no Banco de Dados");
        }

    }
}
