package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.service.ClienteService;
import com.terraco.terracoDaCida.service.LoginService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

    public String dtoToEntity(String valor){
        return valor;
    }


}