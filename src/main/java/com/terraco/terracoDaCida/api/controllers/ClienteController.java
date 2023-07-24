package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ClienteDTOView;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cliente/", produces="application/json")
@JsonDeserialize
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper = ClienteMapper.INSTANCE;

    @PostMapping(value = "/criar/")
    public ResponseEntity criar(@RequestBody ClienteDTO dto){

        Cliente cliente = mapper.toEntity(dto);
        ClienteDTOView clienteCriado = service.criar(cliente);
        return new ResponseEntity(clienteCriado, HttpStatus.CREATED);

    }

    @GetMapping(value = "/{id}/")
    public ResponseEntity buscarCliente(@PathVariable("id") Long id)
    {
        ClienteDTOView dto = mapper.toDto(service.buscarClienteNaoExcluido(id));
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/todos/")
    public ResponseEntity buscarTodosOsClientes(){

        List<ClienteDTOView> clientes = service.buscarTodosOsClientesNaoExcluidos();
        return new ResponseEntity(clientes, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/atualizar/")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO dto){
        Cliente cliente = service.buscarClienteNaoExcluido(id);
        ClienteDTOView clienteAtualizado = service.atualizar(cliente, dto.getCelCliente(), dto.getEmailCliente());
        return new ResponseEntity(clienteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/deletar/")
    public ResponseEntity deletar(@PathVariable("id") Long id){

        Cliente cliente = service.buscarClienteNaoExcluido(id);
        ClienteDTOView clienteDeletado = service.deletar(cliente);
        return new ResponseEntity(clienteDeletado, HttpStatus.OK);
    }

}
