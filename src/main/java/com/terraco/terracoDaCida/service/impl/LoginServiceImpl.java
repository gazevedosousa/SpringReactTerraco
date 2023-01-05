package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.exceptions.ErroAtualizacaoLogin;
import com.terraco.terracoDaCida.exceptions.ErroAutenticacao;
import com.terraco.terracoDaCida.exceptions.ErroExclusaoLogin;
import com.terraco.terracoDaCida.exceptions.RegraNegocioException;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.repository.LoginRepository;
import com.terraco.terracoDaCida.service.LoginService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private LoginRepository repository;

    @Autowired
    public LoginServiceImpl(LoginRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Login autenticar(String noUsuario, String coSenha) {
        Optional<Login> login = repository.findByNoUsuarioAndDhExclusaoIsNull(noUsuario);

        if(login.isEmpty()){
            throw new ErroAutenticacao("Usuário não encontrado na base de dados");
        }

        if(!login.get().getCoSenha().equals(coSenha)){
            throw new ErroAutenticacao("Senha inválida");
        }

        return login.get();
    }

    @Override
    @Transactional
    public Login criarLogin(Login login) {
        validarLogin(login.getNoUsuario());
        return repository.save(login);
    }
    @Override
    @Transactional
    public Login alterarSenha(Login login, String coSenhaNova) {
        Optional<Login> buscaLogin = repository.findByNoUsuarioAndDhExclusaoIsNull(login.getNoUsuario());

        if(buscaLogin.isEmpty()){
            throw new ErroAtualizacaoLogin("Usuário não encontrado na base de dados");
        }

        Login loginAtualizado = buscaLogin.get();
        loginAtualizado.setCoSenha(coSenhaNova);
        return repository.save(loginAtualizado);
    }

    @Override
    public Login deletarLogin(Login login) {
        Optional<Login> buscaLogin = repository.findByNoUsuarioAndDhExclusaoIsNull(login.getNoUsuario());

        if(buscaLogin.isEmpty()){
            throw new ErroExclusaoLogin("Usuário não encontrado na base de dados");
        }

        Login loginDeletado = buscaLogin.get();
        loginDeletado.setDhExclusao(LocalDate.now());
        return repository.save(loginDeletado);
    }

    @Override
    public void validarLogin(String noUsuario) {
        boolean existe = repository.existsByNoUsuarioAndDhExclusaoIsNull(noUsuario);

        if(existe){
            throw new RegraNegocioException("Usuário já cadastrado!");
        }
    }
}
