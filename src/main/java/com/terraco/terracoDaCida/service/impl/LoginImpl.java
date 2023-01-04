package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.exceptions.RegraNegocioException;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.repository.LoginRepository;
import com.terraco.terracoDaCida.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginImpl implements LoginService {

    private LoginRepository repository;

    @Autowired
    public LoginImpl(LoginRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Login autenticar(String usuario, String senha) {
        return null;
    }

    @Override
    public Login criarLogin(Login login) {
        return null;
    }

    @Override
    public Login atualizarLogin(Login login) {
        return null;
    }

    @Override
    public Login deletarLogin(Login login) {
        return null;
    }

    @Override
    public void validarUsuario(String usuario) {
        boolean existe = repository.existsByNoUsuario(usuario);

        if(existe){
            throw new RegraNegocioException("Usuário já cadastrado!");
        }
    }
}
