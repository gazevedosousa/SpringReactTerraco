package com.terraco.terracoDaCida.exceptions;

public class ErroLoginService extends RuntimeException {

    public ErroLoginService(String msg){
        super(msg);
    }
}
