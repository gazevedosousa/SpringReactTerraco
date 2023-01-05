package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.entity.TipoLogin;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LoginRepositoryTest {

    @Autowired
    LoginRepository repository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmLoginComBaseNoNomeDoUsuario(){
        //cenário
        Login login = criaLogin();
        entityManager.persist(login);
        //ação
        boolean existe = repository.existsByNoUsuarioAndDhExclusaoIsNull("Admin");
        //verificação
        Assertions.assertTrue(existe);
    }

    @Test
    public void deveRetornarFalsoQuandoNaoTiverLoginComNomeDeUsuarioNaBase(){
        //cenário

        //ação
        boolean naoExiste = repository.existsByNoUsuarioAndDhExclusaoIsNull("admin123");
        //verificação
        Assertions.assertFalse(naoExiste);
    }

    @Test
    public void deveRetornarFalsoQuandoTiverLoginExcluidoNaBaseEBuscarOLoginPeloNomeDeUsuario(){
        //cenário
        Login login = criaLogin();
        login.setDhExclusao(LocalDate.now());
        entityManager.persist(login);
        //ação
        Optional<Login> loginDoBanco = repository.findByNoUsuarioAndDhExclusaoIsNull("Admin");
        //verificação
        Assertions.assertFalse(loginDoBanco.isPresent());
    }

    @Test
    public void deveRetornarFalsoQuandoTiverLoginExcluidoNaBaseEVerificarSeExisteLoginNaBase(){
        //cenário
        Login login = criaLogin();
        login.setDhExclusao(LocalDate.now());
        entityManager.persist(login);
        //ação
        boolean naoExiste = repository.existsByNoUsuarioAndDhExclusaoIsNull("Admin");
        //verificação
        Assertions.assertFalse(naoExiste);
    }

    @Test
    public void deveBuscarLoginComBaseNoNomeDeUsuario(){
        //cenário
        Login login = criaLogin();
        entityManager.persist(login);
        //ação
        Optional<Login> loginDoBanco = repository.findByNoUsuarioAndDhExclusaoIsNull("Admin");
        //verificação
        Assertions.assertTrue(loginDoBanco.isPresent());
    }

    @Test
    public void deveRetornarLoginVazioComBaseNoNomeDeUsuario(){
        //cenário

        //ação
        Optional<Login> loginDoBanco = repository.findByNoUsuarioAndDhExclusaoIsNull("Admin");
        //verificação
        Assertions.assertFalse(loginDoBanco.isPresent());
    }

    public static TipoLogin criaTipoLogin(){

        return TipoLogin.builder()
                .noTipoLogin("Administrador")
                .dhCriacao(LocalDate.now())
                .dhAtualizacao(LocalDate.now())
                .build();
    }

    public static Login criaLogin(){
        TipoLogin tipoLogin = criaTipoLogin();

        return Login.builder()
                .noUsuario("Admin")
                .coSenha("Admin")
                .tipoLogin(tipoLogin)
                .dhCriacao(LocalDate.now())
                .dhAtualizacao(LocalDate.now())
                .build();
    }
}
