package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.Util.CriptografiaUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
@Mapper
public class LoginQualifier {
    static protected LoginQualifier FIRST_INSTANCE;

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

}