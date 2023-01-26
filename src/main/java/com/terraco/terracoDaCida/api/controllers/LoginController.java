package com.terraco.terracoDaCida.api.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.terraco.terracoDaCida.api.dto.LoginDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.exceptions.ErroLoginService;
import com.terraco.terracoDaCida.mapper.LoginMapper;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/login")
@JsonDeserialize
@RequiredArgsConstructor
public class LoginController {

    private final LoginService service;
    private final LoginMapper mapper = LoginMapper.INSTANCE;

    @PostMapping(value = "/autenticar")
    public ResponseEntity autenticar(@RequestBody LoginDTO dto)
    {
        try{
            LoginDTOView login = mapper.toDto(service.autenticar(dto.getNoUsuario(), dto.getCoSenha()));
            return new ResponseEntity(login, HttpStatus.OK);
        }catch (ErroLoginService | NoSuchAlgorithmException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/criar")
    public ResponseEntity criar(@RequestBody LoginDTO dto){

        Login login = mapper.toEntity(dto);

        try{
            LoginDTOView loginCriado = service.criarLogin(login);
            return new ResponseEntity(loginCriado, HttpStatus.CREATED);
        }catch (ErroLoginService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscarLogin(@PathVariable("id") Long id)
    {
        try{
            LoginDTOView dto = mapper.toDto(service.buscarLogin(id));
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (ErroLoginService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/todos")
    public ResponseEntity buscarTodosOsLogins(){
        try {
            List<LoginDTOView> loginDTOViews = service.buscarTodosOsLogins();
            return new ResponseEntity(loginDTOViews, HttpStatus.OK);
        } catch (ErroLoginService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}/atualizar")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LoginDTO dto){
        Login login = service.buscarLogin(id);
        try{
            LoginDTOView loginAtualizado = service.alterarSenha(login, dto.getCoSenha());
            return new ResponseEntity(loginAtualizado, HttpStatus.OK);
        }catch (ErroLoginService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(value = "/{id}/deletar")
    public ResponseEntity deletar(@PathVariable("id") Long id){

        Login login = service.buscarLogin(id);
        try{
            LoginDTOView loginDeletado = service.deletarLogin(login);
            return new ResponseEntity(loginDeletado, HttpStatus.OK);
        }catch (ErroLoginService e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
