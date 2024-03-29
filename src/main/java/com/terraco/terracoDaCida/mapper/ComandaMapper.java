package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTO;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import com.terraco.terracoDaCida.model.entity.Produto;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ComandaQualifier.class)
public interface ComandaMapper {

    ComandaMapper INSTANCE = Mappers.getMapper(ComandaMapper.class);

    @Named("dtoToEntity")
    default Cliente dtoToEntity(Long id) {
        return ComandaQualifier.getFirstInstance()
                .dtoToEntity(id);
    }

    @Named("entityToDTO")
    default String entityToDTO(Cliente cliente) {
        return ComandaQualifier.getFirstInstance()
                .entityToDTO(cliente);
    }

    @Named("entityToDTOValorComanda")
    default BigDecimal entityToDTOValorComanda(Long idComanda) {
        return ComandaQualifier.getFirstInstance()
                .entityToDTOValorComanda(idComanda);
    }

    @Named("dtoViewToEntity")
    default Cliente dtoViewToEntity(String noCliente) {
        return ComandaQualifier.getFirstInstance()
                .dtoViewToEntity(noCliente);
    }

    @Mapping(source = "cliente", target = "noCliente", qualifiedByName = "entityToDTO")
    @Mapping(source = "id" , target = "vrComanda", qualifiedByName = "entityToDTOValorComanda")
    @Mapping(source = "dataCriacao", target = "dtComanda")
    ComandaDTOView toDto(Comanda comanda);

    @Mapping(source = "idCliente", target = "cliente", qualifiedByName = "dtoToEntity")
    Comanda toEntity(ComandaDTO dto);

    @Mapping(source = "noCliente", target = "cliente", qualifiedByName = "dtoViewToEntity")
    Comanda toEntityFromDtoView(ComandaDTOView dto);

}