package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.TipoProdutoDTO;
import com.terraco.terracoDaCida.api.dto.TipoProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ProdutoQualifier.class)
public interface TipoProdutoMapper {

    TipoProdutoMapper INSTANCE = Mappers.getMapper(TipoProdutoMapper.class);

    TipoProdutoDTOView toDto(TipoProduto tipoProduto);

    TipoProduto toEntity(TipoProdutoDTO dto);

}