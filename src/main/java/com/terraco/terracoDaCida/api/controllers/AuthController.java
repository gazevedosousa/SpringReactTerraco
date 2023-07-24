package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.JwtAuthDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.mapper.LoginMapper;
import com.terraco.terracoDaCida.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuario/", produces="application/json")
@JsonDeserialize
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping(value = "/autenticar/")
    public ResponseEntity autenticar(@RequestBody LoginDTO dto) {
        JwtAuthDTO token = service.signin(dto);
        return new ResponseEntity(token, HttpStatus.OK);
    }

    @PostMapping(value = "/criar/")
    public ResponseEntity criar(@RequestBody LoginDTO dto)
    {
        LoginDTOView login = service.signup(dto);
        return new ResponseEntity(login, HttpStatus.CREATED);
    }
}