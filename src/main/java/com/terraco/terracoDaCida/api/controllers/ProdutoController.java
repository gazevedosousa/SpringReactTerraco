package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.ProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ProdutoDTOView;
import com.terraco.terracoDaCida.mapper.ProdutoMapper;
import com.terraco.terracoDaCida.model.entity.Produto;
import com.terraco.terracoDaCida.service.ProdutoService;
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
@RequestMapping(value = "/api/produto")
@JsonDeserialize
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoMapper mapper = ProdutoMapper.INSTANCE;

    @PostMapping(value = "/criar")
    public ResponseEntity criar(@RequestBody ProdutoDTO dto)
    {
        Produto produto = mapper.toEntity(dto);
        ProdutoDTOView produtoCriado = service.criar(produto);
        return new ResponseEntity(produtoCriado, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscarProduto(@PathVariable("id") Long id)
    {
        ProdutoDTOView dto = mapper.toDto(service.buscarProdutoNaoExcluido(id));
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/todos")
    public ResponseEntity buscarTodosOsProdutos()
    {
        List<ProdutoDTOView> produtos = service.buscarTodosOsProdutosNaoExcluidos();
        return new ResponseEntity(produtos, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/atualizar")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto)
    {
        Produto produto = service.buscarProdutoNaoExcluido(id);
        ProdutoDTOView produtoAtualizado = service.atualizar(produto, dto.getVrProduto(), dto.getIdTipoProduto());
        return new ResponseEntity(produtoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/deletar")
    public ResponseEntity deletar(@PathVariable("id") Long id){

        Produto produto = service.buscarProdutoNaoExcluido(id);
        ProdutoDTOView produtoDeletado = service.deletar(produto);
        return new ResponseEntity(produtoDeletado, HttpStatus.OK);
    }
}
