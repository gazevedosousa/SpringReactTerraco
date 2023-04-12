package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.ComandaProdutoMapper;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import com.terraco.terracoDaCida.service.ComandaProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/lancamentos")
@JsonDeserialize
@RequiredArgsConstructor
public class ComandaProdutoController {

    private final ComandaProdutoService service;
    private final ComandaProdutoMapper mapper = ComandaProdutoMapper.INSTANCE;

    @PostMapping(value = "/criar")
    public ResponseEntity criar(@RequestBody ComandaProdutoDTO dto)
    {
        ComandaProduto comandaProduto = mapper.toEntity(dto);
        ComandaProdutoDTOView comandaProdutoCriada = service.criar(comandaProduto);
        return new ResponseEntity(comandaProdutoCriada, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscarComandaProduto(@PathVariable("id") Long id)
    {
        ComandaProdutoDTOView dto = mapper.toDto(service.buscarComandaProduto(id));
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/comanda/{idComanda}")
    public ResponseEntity buscarProdutosDeComanda(@PathVariable("idComanda") Long idComanda)
    {
        List<ComandaProdutoDTOView> comandas = service.buscarProdutosDeUmaComanda(idComanda);
        return new ResponseEntity(comandas, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/deletar")
    public ResponseEntity deletar(@PathVariable("id") Long id)
    {
        ComandaProduto comandaProduto = service.buscarComandaProduto(id);
        ComandaProdutoDTOView comandaProdutoDeletada = service.deletar(comandaProduto);
        return new ResponseEntity(comandaProdutoDeletada, HttpStatus.OK);
    }

    @GetMapping(value = "/porData/{data}")
    public ResponseEntity buscarProdutoPorData(@PathVariable("data") String data)
    {
        try{
            List<ComandaProdutoDTOView> dto = service.buscarProdutosEmUmaData(data);
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/porMes/{data}")
    public ResponseEntity buscarProdutoPorMes(@PathVariable("data") String data)
    {
        try{
            List<ComandaProdutoDTOView> dto = service.buscarProdutosEmUmMes(data);
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
