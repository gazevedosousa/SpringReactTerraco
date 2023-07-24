package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.model.entity.Login;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoginService {

    Login autenticar(String NoUsuario, String coSenha);

    LoginDTOView criarLogin(Login login);

    LoginDTOView alterarSenha(Login login, String coSenhaNova) throws NoSuchAlgorithmException;

    LoginDTOView deletarLogin(Login login);

    Login buscarLogin(Long id);

    List<LoginDTOView> buscarTodosOsLogins();

    void validarLogin(String noUsuario);

}
