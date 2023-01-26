package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Produto;
import com.terraco.terracoDaCida.service.ComandaService;
import com.terraco.terracoDaCida.service.ProdutoService;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Mapper
public class ComandaProdutoQualifier {
    @Autowired
    private ComandaService comandaService;
    @Autowired
    private ProdutoService produtoService;

    static protected ComandaProdutoQualifier FIRST_INSTANCE;

    public ComandaProdutoQualifier() {
        if(FIRST_INSTANCE == null) {
            FIRST_INSTANCE = this;
        }
    }

    public static ComandaProdutoQualifier getFirstInstance(){
        return FIRST_INSTANCE;
    }

    public Comanda comandaDtoToEntity(Long id){
        return comandaService.buscarComanda(id);
    }
    public Produto produtoDtoToEntity(Long id){
        return produtoService.buscarProduto(id);
    }
    public Long comandaEntityToDTO(Comanda comanda){
        return comanda.getId();
    }
    public Long produtoEntityToDTO(Produto produto){
        return produto.getId();
    }
    public String noClienteEntityToDTO(Comanda comanda){
        return String.valueOf(comanda.getCliente().getNoCliente());
    }
    public String noProdutoEntityToDTO(Produto produto){
        return String.valueOf(produto.getNoProduto());
    }
    public BigDecimal vrProdutoEntityToDTO(Produto produto){
        return produto.getVrProduto();
    }

}