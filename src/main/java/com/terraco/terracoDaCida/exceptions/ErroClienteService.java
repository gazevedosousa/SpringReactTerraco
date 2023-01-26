package com.terraco.terracoDaCida.exceptions;

public class ErroClienteService extends RuntimeException {

    public ErroClienteService(String msg){
        super(msg);
    }
}
