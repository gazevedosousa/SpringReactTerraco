package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.model.entity.Login;

public interface LoginService {

    Login autenticar(String usuario, String senha);

    Login criarLogin(Login login);

    Login atualizarLogin(Login login);

    Login deletarLogin(Login login);

    void validarUsuario(String usuario);
}
