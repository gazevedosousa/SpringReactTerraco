package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.model.entity.Login;

public interface LoginService {

    Login autenticar(String NoUsuario, String coSenha);

    Login criarLogin(Login login);

    Login alterarSenha(Login login, String coSenhaNova);

    Login deletarLogin(Login login);

    void validarLogin(String noUsuario);
}
