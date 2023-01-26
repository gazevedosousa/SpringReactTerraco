package com.terraco.terracoDaCida.exceptions;

public class ErroComandaService extends RuntimeException {

    public ErroComandaService(String msg){
        super(msg);
    }
}
