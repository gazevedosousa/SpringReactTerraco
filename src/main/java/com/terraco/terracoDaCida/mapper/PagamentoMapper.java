package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.PagamentoDTO;
import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PagamentoQualifier.class)
public interface PagamentoMapper {

    PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);

    @Named("dtoToEntity")
    default Comanda dtoToEntity(Long idComanda){
        return PagamentoQualifier.getFirstInstance()
                .dtoToEntity(idComanda);
    }
    @Named("entityToDTO")
    default Long entityToDTO(Comanda comanda){
        return PagamentoQualifier.getFirstInstance()
                .entityToDTO(comanda);
    }

    @Named("entityToDTOCliente")
    default String entityToDTOCliente(Comanda comanda){
        return PagamentoQualifier.getFirstInstance()
                .entityToDTOCliente(comanda);
    }

    @Mapping(source = "comanda", target = "noCliente", qualifiedByName = "entityToDTOCliente")
    @Mapping(source = "comanda", target = "idComanda", qualifiedByName = "entityToDTO")
    PagamentoDTOView toDto(Pagamento pagamento);

    @Mapping(source = "idComanda", target = "comanda", qualifiedByName = "dtoToEntity")
    Pagamento toEntity(PagamentoDTO dto);

}