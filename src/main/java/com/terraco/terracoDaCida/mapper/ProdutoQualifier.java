package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.model.entity.TipoProduto;
import com.terraco.terracoDaCida.service.TipoProdutoService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class ProdutoQualifier {
    @Autowired
    public TipoProdutoService tipoProdutoService;
    static protected ProdutoQualifier FIRST_INSTANCE;

    public ProdutoQualifier() {
        if(FIRST_INSTANCE == null) {
            FIRST_INSTANCE = this;
        }
    }


    public static ProdutoQualifier getFirstInstance(){
        return FIRST_INSTANCE;
    }

    public TipoProduto dtoToEntity(Long id){
        return tipoProdutoService.buscarTipoProduto(id);
    }

    public String entityToDTO(TipoProduto tipoProduto){
        return tipoProduto.getNoTipoProduto();
    }
    public Long entityToDTOIdTipoProduto(TipoProduto tipoProduto){
        return tipoProduto.getId();
    }

}