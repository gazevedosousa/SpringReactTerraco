package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.ProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.TipoProdutoDTO;
import com.terraco.terracoDaCida.api.dto.TipoProdutoDTOView;
import com.terraco.terracoDaCida.exceptions.ErroProdutoService;
import com.terraco.terracoDaCida.exceptions.ErroTipoProdutoService;
import com.terraco.terracoDaCida.mapper.TipoProdutoMapper;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
import com.terraco.terracoDaCida.service.TipoProdutoService;
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
@RequestMapping(value = "/api/tipoProduto")
@JsonDeserialize
@RequiredArgsConstructor
public class TipoProdutoController {

    private final TipoProdutoService service;
    private final TipoProdutoMapper mapper = TipoProdutoMapper.INSTANCE;

    @PostMapping(value = "/criar")
    public ResponseEntity criar(@RequestBody TipoProdutoDTO dto){

        TipoProduto tipoProduto = mapper.toEntity(dto);

        try{
            TipoProdutoDTOView tipoProdutoCriado = service.criar(tipoProduto);
            return new ResponseEntity(tipoProdutoCriado, HttpStatus.CREATED);
        }catch (ErroTipoProdutoService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscarTipoProduto(@PathVariable("id") Long id)
    {
        try{
            TipoProdutoDTOView dto = mapper.toDto(service.buscarTipoProduto(id));
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ErroTipoProdutoService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/todos")
    public ResponseEntity buscarTodosOsTiposDeProduto()
    {
        try {
            List<TipoProdutoDTOView> tiposDeProduto = service.buscarTodosOsTiposProduto();
            return new ResponseEntity(tiposDeProduto, HttpStatus.OK);
        } catch (ErroProdutoService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}/deletar")
    public ResponseEntity deletar(@PathVariable("id") Long id){

        TipoProduto tipoProduto = service.buscarTipoProduto(id);
        try{
            TipoProdutoDTOView tipoProdutoDeletado = service.deletar(tipoProduto);
            return new ResponseEntity(tipoProdutoDeletado, HttpStatus.OK);
        }catch (ErroTipoProdutoService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
