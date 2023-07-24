package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
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
@RequestMapping(value = "/api/comanda/", produces="application/json")
@JsonDeserialize
@RequiredArgsConstructor
public class ComandaController {

    private final ComandaService service;
    private final ComandaMapper mapper = ComandaMapper.INSTANCE;

    @PostMapping(value = "/criar/")
    public ResponseEntity criar(@RequestBody ComandaDTO dto)
    {
        Comanda comanda = mapper.toEntity(dto);
        ComandaDTOView comandaCriada = service.criar(comanda);
        return new ResponseEntity(comandaCriada, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/")
    public ResponseEntity buscarComanda(@PathVariable("id") Long id)
    {
        ComandaDTOView dto = mapper.toDto(service.buscarComanda(id));
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/todas/")
    public ResponseEntity buscarTodasAsComandas()
    {
        List<ComandaDTOView> comandas = service.buscarTodasAsComandas();
        return new ResponseEntity(comandas, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/fecharComanda/")
    public ResponseEntity fecharComanda(@PathVariable("id") Long id)
    {
        Comanda comanda = service.buscarComanda(id);
        ComandaDTOView comandaFechada = service.fecharComanda(comanda);
        return new ResponseEntity(comandaFechada, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/reabrirComanda/")
    public ResponseEntity reabrirComanda(@PathVariable("id") Long id)
    {
        Comanda comanda = service.buscarComanda(id);
        ComandaDTOView comandaReaberta = service.reabrirComanda(comanda);
        return new ResponseEntity(comandaReaberta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/deletar/")
    public ResponseEntity deletar(@PathVariable("id") Long id)
    {
        ComandaDTOView dto = mapper.toDto(service.buscarComanda(id));
        Comanda comanda = mapper.toEntityFromDtoView(dto);
        ComandaDTOView comandaDeletada = service.deletar(comanda);

        return new ResponseEntity(comandaDeletada, HttpStatus.OK);
    }
}
