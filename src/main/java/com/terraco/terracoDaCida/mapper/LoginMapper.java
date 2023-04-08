package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.AutenticacaoDTOView;
import com.terraco.terracoDaCida.api.dto.LoginDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.model.entity.Login;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = LoginQualifier.class)
public interface LoginMapper {

    LoginMapper INSTANCE = Mappers.getMapper(LoginMapper.class);

    @Named("dtoToEntity")
    default byte[] dtoToEntity(String valor) {
        return LoginQualifier.getFirstInstance()
                .dtoToEntity(valor);
    }

    @Named("dtoToEntityAuth")
    default UUID dtoToEntityAuth(String noUsuario) {
        return LoginQualifier.getFirstInstance()
                .dtoToEntityAuth(noUsuario);
    }

    AutenticacaoDTOView toAuthDto(Login login);

    LoginDTOView toDto(Login login);

    @Mapping(source = "coSenha", target = "coSenha", qualifiedByName = "dtoToEntity")
    @Mapping(source = "noUsuario",  target = "token", qualifiedByName = "dtoToEntityAuth")
    Login toEntity(LoginDTO dto);
}