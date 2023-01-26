package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
import com.terraco.terracoDaCida.exceptions.ErroComandaService;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.service.ComandaService;
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
@RequestMapping(value = "/api/comanda")
@JsonDeserialize
@RequiredArgsConstructor
public class ComandaController {

    private final ComandaService service;
    private final ComandaMapper mapper = ComandaMapper.INSTANCE;

    @PostMapping(value = "/criar")
    public ResponseEntity criar(@RequestBody ComandaDTO dto){

        Comanda comanda = mapper.toEntity(dto);

        try{
            ComandaDTOView comandaCriada = service.criar(comanda);
            return new ResponseEntity(comandaCriada, HttpStatus.CREATED);
        }catch (ErroComandaService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscarComanda(@PathVariable("id") Long id)
    {
        try{
            ComandaDTOView dto = mapper.toDto(service.buscarComanda(id));
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ErroComandaService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/todas")
    public ResponseEntity buscarTodasAsComandas(){
        try {
            List<ComandaDTOView> comandas = service.buscarTodasAsComandas();
            return new ResponseEntity(comandas, HttpStatus.OK);
        } catch (ErroComandaService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}/atualizar")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ComandaDTO dto){
        Comanda comanda = service.buscarComanda(id);
        try{
            ComandaDTOView comandaAtualizada = service.alterarSituacao(comanda, dto.getSituacaoComanda());
            return new ResponseEntity(comandaAtualizada, HttpStatus.OK);
        }catch (ErroComandaService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}/deletar")
    public ResponseEntity deletar(@PathVariable("id") Long id){

        Comanda comanda = service.buscarComanda(id);
        try{
            ComandaDTOView comandaDeletada = service.deletar(comanda);
            return new ResponseEntity(comandaDeletada, HttpStatus.OK);
        }catch (ErroComandaService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
