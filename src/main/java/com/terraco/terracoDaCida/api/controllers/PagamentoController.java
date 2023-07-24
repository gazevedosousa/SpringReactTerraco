package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.PagamentoDTO;
import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.PagamentoMapper;
import com.terraco.terracoDaCida.model.entity.Pagamento;
import com.terraco.terracoDaCida.service.PagamentoService;
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
@RequestMapping(value = "/api/pagamento", produces="application/json")
@JsonDeserialize
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService service;
    private final PagamentoMapper mapper = PagamentoMapper.INSTANCE;

    @PostMapping(value = "/criar")
    public ResponseEntity criar(@RequestBody PagamentoDTO dto){

        Pagamento pagamento = mapper.toEntity(dto);

        try{
            PagamentoDTOView pagamentoCriado = service.pagarParcial(pagamento);
            return new ResponseEntity(pagamentoCriado, HttpStatus.CREATED);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscarPagamento(@PathVariable("id") Long id)
    {
        try{
            PagamentoDTOView dto = mapper.toDto(service.buscarPagamento(id));
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(value = "/porData/{data}")
    public ResponseEntity buscarPagamentoPorData(@PathVariable("data") String data)
    {
        try{
            List<PagamentoDTOView> dto = service.buscarPagamentosEmUmaData(data);
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/porMes/{data}")
    public ResponseEntity buscarPagamentoPorMes(@PathVariable("data") String data)
    {
        try{
            List<PagamentoDTOView> dto = service.buscarPagamentosEmUmMes(data);
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/porCliente/{id}")
    public ResponseEntity buscarPagamentoPorMes(@PathVariable("id") Long id)
    {
        try{
            List<PagamentoDTOView> dto = service.buscarPagamentosDeUmCliente(id);
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/comanda/{idComanda}")
    public ResponseEntity buscarPagamentoDeUmaComanda(@PathVariable("idComanda") Long id)
    {
        try{
            List<PagamentoDTOView> dto = service.buscarPagamentosDeUmaComanda(id);
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}/deletar")
    public ResponseEntity deletar(@PathVariable("id") Long id){

        Pagamento pagamento = service.buscarPagamento(id);
        try{
            PagamentoDTOView pagamentoDeletado = service.estornarPagamento(pagamento);
            return new ResponseEntity(pagamentoDeletado, HttpStatus.OK);
        }catch (ElementoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
