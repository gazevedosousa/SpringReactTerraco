package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.LoginDTO;
import com.terraco.terracoDaCida.api.dto.TipoLoginDTO;
import com.terraco.terracoDaCida.exceptions.RegraNegocioException;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.entity.TipoLogin;
import com.terraco.terracoDaCida.model.repository.TipoLoginRepository;
import com.terraco.terracoDaCida.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/login", method = {RequestMethod.GET, RequestMethod.POST})
@JsonDeserialize
public class LoginController {

    private LoginService service;
    @Autowired
    private TipoLoginRepository tipoLoginRepository;

    public LoginController (LoginService service){
        this.service = service;
    }


    @PostMapping
    public ResponseEntity criar(@RequestBody LoginDTO dto){
        Optional<TipoLogin> tipoLogin = tipoLoginRepository.findByCoTipoLoginAndDhExclusaoIsNull(dto.getTipoLogin());

        if(tipoLogin.isEmpty()){
            return ResponseEntity.badRequest().body("erro");
        }
        Login login = Login.builder()
                           .noUsuario(dto.getNoUsuario())
                           .coSenha(dto.getCoSenha())
                           .tipoLogin(tipoLogin.get())
                           .dhCriacao(dto.getDhCriacao())
                           .dhAtualizacao(dto.getDhAtualizacao())
                           .build();
        try{
            Login loginCriado = service.criarLogin(login);
            return new ResponseEntity(login, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
