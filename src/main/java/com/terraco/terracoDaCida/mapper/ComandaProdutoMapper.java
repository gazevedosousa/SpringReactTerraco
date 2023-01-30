package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import com.terraco.terracoDaCida.model.entity.Produto;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ComandaProdutoQualifier.class)

public interface ComandaProdutoMapper {

    ComandaProdutoMapper INSTANCE = Mappers.getMapper(ComandaProdutoMapper.class);
    @Named("comandaDtoToEntity")
    default Comanda comandaDtoToEntity(Long id){
        return ComandaProdutoQualifier.getFirstInstance()
                .comandaDtoToEntity(id);
    }
    @Named("produtoDtoToEntity")
    default Produto produtoDtoToEntity(Long id){
        return ComandaProdutoQualifier.getFirstInstance()
                .produtoDtoToEntity(id);
    }
    @Named("comandaEntityToDTO")
    default Long comandaEntityToDTO(Comanda comanda){
        return ComandaProdutoQualifier.getFirstInstance()
                .comandaEntityToDTO(comanda);
    }

    @Named("noClienteEntityToDTO")
    default String noClienteEntityToDTO(Comanda comanda){
        return ComandaProdutoQualifier.getFirstInstance()
                .noClienteEntityToDTO(comanda);
    }
    @Named("produtoEntityToDTO")
    default Long produtoEntityToDTO(Produto produto){
        return ComandaProdutoQualifier.getFirstInstance()
                .produtoEntityToDTO(produto);
    }

    @Named("noProdutoEntityToDTO")
    default String noProdutoEntityToDTO(Produto produto){
        return ComandaProdutoQualifier.getFirstInstance()
                .noProdutoEntityToDTO(produto);
    }


    @Mapping(source = "comanda", target = "idComanda", qualifiedByName = "comandaEntityToDTO")
    @Mapping(source = "comanda", target = "noCliente", qualifiedByName = "noClienteEntityToDTO")
    @Mapping(source = "produto", target = "idProduto", qualifiedByName = "produtoEntityToDTO")
    @Mapping(source = "produto", target = "noProduto", qualifiedByName = "noProdutoEntityToDTO")
    @Mapping(source = "vrComandaProduto", target = "vrProduto")
    ComandaProdutoDTOView toDto(ComandaProduto comandaProduto);

    @Mapping(source = "idComanda", target = "comanda", qualifiedByName = "comandaDtoToEntity")
    @Mapping(source = "idProduto", target = "produto", qualifiedByName = "produtoDtoToEntity")
    @BeanMapping(builder = @Builder(disableBuilder = true))
    ComandaProduto toEntity(ComandaProdutoDTO dto);

    @Mapping(source = "idComanda", target = "comanda", qualifiedByName = "comandaDtoToEntity")
    @Mapping(source = "idProduto", target = "produto", qualifiedByName = "produtoDtoToEntity")
    @Mapping(source = "vrProduto", target = "vrComandaProduto")
    ComandaProduto viewToEntity(ComandaProdutoDTOView dto);

    @AfterMapping
    default void incluiVrComandaProduto(ComandaProdutoDTO dto, @MappingTarget ComandaProduto comandaProduto){
        comandaProduto.setVrComandaProduto(comandaProduto.getProduto().getVrProduto());
    }

}