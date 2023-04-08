package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.Util.CriptografiaUtil;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.service.ComandaService;
import com.terraco.terracoDaCida.service.LoginService;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
@Mapper
public class LoginQualifier {
    static protected LoginQualifier FIRST_INSTANCE;

    @Autowired
    private LoginService service;

    public LoginQualifier() {
        if(FIRST_INSTANCE == null) {
            FIRST_INSTANCE = this;
        }
    }

    public static LoginQualifier getFirstInstance(){
        return FIRST_INSTANCE;
    }

    public byte[] dtoToEntity(String valor){
        try {
            return CriptografiaUtil.criptografar(valor);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID dtoToEntityAuth(String noUsuario){
        return UUID.randomUUID();
    }
}