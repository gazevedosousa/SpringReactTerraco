package com.terraco.terracoDaCida.exceptions;

public class ErroPagamentoService extends RuntimeException {

    public ErroPagamentoService(String msg){
        super(msg);
    }
}
