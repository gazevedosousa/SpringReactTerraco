package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.exceptions.ErroComandaProdutoService;
import com.terraco.terracoDaCida.exceptions.ErroComandaService;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.mapper.ComandaProdutoMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import com.terraco.terracoDaCida.service.ComandaProdutoService;
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
@RequestMapping(value = "/api/lancamentos")
@JsonDeserialize
@RequiredArgsConstructor
public class ComandaProdutoController {

    private final ComandaProdutoService service;
    private final ComandaProdutoMapper mapper = ComandaProdutoMapper.INSTANCE;

    @PostMapping(value = "/criar")
    public ResponseEntity criar(@RequestBody ComandaProdutoDTO dto){

        ComandaProduto comandaProduto = mapper.toEntity(dto);

        try{
            ComandaProdutoDTOView comandaProdutoCriada = service.criar(comandaProduto);
            return new ResponseEntity(comandaProdutoCriada, HttpStatus.CREATED);
        }catch (ErroComandaProdutoService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscarComandaProduto(@PathVariable("id") Long id)
    {
        try{
            ComandaProdutoDTOView dto = mapper.toDto(service.buscarComandaProduto(id));
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ErroComandaProdutoService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/comanda/{idComanda}")
    public ResponseEntity buscarProdutosDeComanda(@PathVariable("idComanda") Long idComanda){
        try {
            List<ComandaProdutoDTOView> comandas = service.buscarProdutosDeUmaComanda(idComanda);

            return new ResponseEntity(comandas, HttpStatus.OK);
        } catch (ErroComandaProdutoService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}/deletar")
    public ResponseEntity deletar(@PathVariable("id") Long id){

        ComandaProduto comandaProduto = service.buscarComandaProduto(id);
        try{
            ComandaProdutoDTOView comandaProdutoDeletada = service.deletar(comandaProduto);
            return new ResponseEntity(comandaProdutoDeletada, HttpStatus.OK);
        }catch (ErroComandaProdutoService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
