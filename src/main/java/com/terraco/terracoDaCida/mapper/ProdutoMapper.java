package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.ProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.Produto;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ProdutoQualifier.class)
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    @Named("dtoToEntity")
    default TipoProduto dtoToEntity(Long id){
        return ProdutoQualifier.getFirstInstance()
                .dtoToEntity(id);
    }

    @Named("entityToDTO")
    default String entityToDTO(TipoProduto tipoProduto){
        return ProdutoQualifier.getFirstInstance()
                .entityToDTO(tipoProduto);
    }

    @Mapping(source = "tipoProduto", target = "tipoProduto", qualifiedByName = "entityToDTO")
    ProdutoDTOView toDto(Produto produto);

    @Mapping(source = "idTipoProduto", target = "tipoProduto", qualifiedByName = "dtoToEntity")
    Produto toEntity(ProdutoDTO dto);


}