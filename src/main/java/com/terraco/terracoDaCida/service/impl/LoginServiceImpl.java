package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.Util.CriptografiaUtil;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.exceptions.ErroLoginService;
import com.terraco.terracoDaCida.mapper.LoginMapper;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.repository.LoginRepository;
import com.terraco.terracoDaCida.service.LoginService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginRepository repository;

    private final LoginMapper mapper = LoginMapper.INSTANCE;

    @Override
    public Login autenticar(String noUsuario, String coSenha) throws NoSuchAlgorithmException {
        Optional<Login> login = repository.findByNoUsuarioAndDataExclusaoIsNull(noUsuario);

        if(login.isEmpty()){
            throw new ErroLoginService("Usuário não encontrado na base de dados");
        }

        if(!Arrays.equals(login.get().getCoSenha(), CriptografiaUtil.criptografar(coSenha))){
            throw new ErroLoginService("Senha inválida");
        }

        return login.get();
    }

    @Override
    @Transactional
    public LoginDTOView criarLogin(Login login) {
        validarLogin(login.getNoUsuario());
        login.setDataCriacao(LocalDateTime.now());
        login.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(login));
    }
    @Override
    @Transactional
    public LoginDTOView alterarSenha(Login login, String coSenhaNova) throws NoSuchAlgorithmException {
        Login loginAtualizado = repository.findByNoUsuarioAndDataExclusaoIsNull(login.getNoUsuario())
                .orElseThrow(() -> new ErroLoginService("Usuário não encontrado na base de dados"));

        loginAtualizado.setCoSenha(CriptografiaUtil.criptografar(coSenhaNova));
        loginAtualizado.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(loginAtualizado));
    }

    @Override
    @Transactional
    public LoginDTOView deletarLogin(Login login) {
        Login loginDeletado = repository.findByNoUsuarioAndDataExclusaoIsNull(login.getNoUsuario())
                .orElseThrow(() -> new ErroLoginService("Usuário não encontrado na base de dados"));

        loginDeletado.setDataExclusao(LocalDateTime.now());
        loginDeletado.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(loginDeletado));
    }

    @Override
    public Login buscarLogin(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ErroLoginService("Usuário não encontrado na base de dados"));
    }

    @Override
    public List<LoginDTOView> buscarTodosOsLogins() {
        List<Login> logins = repository.findAllWhereDataExclusaoIsNull();
        List<LoginDTOView> loginDTOViews = new ArrayList<>();

        logins.forEach(login -> {
            loginDTOViews.add(mapper.toDto(login));
        });

        return loginDTOViews;
    }

    @Override
    public void validarLogin(String noUsuario) {
        boolean existe = repository.existsByNoUsuarioAndDataExclusaoIsNull(noUsuario);

        if(existe){
            throw new ErroLoginService("Usuário já cadastrado!");
        }
    }


}
