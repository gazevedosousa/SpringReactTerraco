package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ClienteDTOView;
import com.terraco.terracoDaCida.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDTOView toDto(Cliente cliente);

    Cliente toEntity(ClienteDTO dto);



}