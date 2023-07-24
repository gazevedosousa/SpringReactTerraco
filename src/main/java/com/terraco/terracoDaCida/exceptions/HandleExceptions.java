package com.terraco.terracoDaCida.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleExceptions extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ElementoNaoEncontradoException.class)
    public ResponseEntity<?> handleException(ElementoNaoEncontradoException e, WebRequest request){

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<?> handleException(RegraNegocioException e, WebRequest request){

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NaoAutorizadoException.class)
    public ResponseEntity<?> handleException(NaoAutorizadoException e, WebRequest request){

        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
