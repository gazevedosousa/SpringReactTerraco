package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.entity.TipoLogin;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginRepositoryTest {

    @Autowired
    LoginRepository repository;
    TipoLoginRepository tipoLoginRepository;

    @Test
    public void deveVerificarAExistenciaDeUmUsuario(){
        TipoLogin tipoLogin = new TipoLogin();

        tipoLogin.setCoTipoLogin(1);
        tipoLogin.setNoTipoLogin("Administrador");
        tipoLogin.setDhCriacao(LocalDate.now());
        tipoLogin.setDhAtualizacao(LocalDate.now());

        Login login = new Login();

        login.setCoLogin(1);
        login.setNoUsuario("admin");
        login.setCoSenha("admin");
        login.setTipoLogin(tipoLogin);
        login.setDhCriacao(LocalDate.now());
        login.setDhAtualizacao(LocalDate.now());

        repository.save(login);

        boolean existe = repository.existsByNoUsuario("admin");

        Assertions.assertTrue(existe);
    }

    @Test
    public void deveRetornarFalsoQuandoNaoTiverUsuarioNaBase(){
        TipoLogin tipoLogin = new TipoLogin();

        tipoLogin.setCoTipoLogin(1);
        tipoLogin.setNoTipoLogin("Administrador");
        tipoLogin.setDhCriacao(LocalDate.now());
        tipoLogin.setDhAtualizacao(LocalDate.now());

        Login login = new Login();

        login.setCoLogin(1);
        login.setNoUsuario("admin");
        login.setCoSenha("admin");
        login.setTipoLogin(tipoLogin);
        login.setDhCriacao(LocalDate.now());
        login.setDhAtualizacao(LocalDate.now());

        repository.save(login);

        boolean naoExiste = repository.existsByNoUsuario("admin123");

        Assertions.assertFalse(naoExiste);
    }
}
