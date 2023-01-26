package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.model.entity.Login;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LoginRepositoryTest {
/*
    @Autowired
    LoginRepository repository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmLoginComBaseNoNomeDoUsuario(){
        //cenário

        entityManager.persist(login);
        //ação
        boolean existe = repository.existsByNoUsuarioAndDataExclusaoIsNull("Admin");
        //verificação
        Assertions.assertTrue(existe);
    }

    @Test
    public void deveRetornarFalsoQuandoNaoTiverLoginComNomeDeUsuarioNaBase(){
        //cenário

        //ação
        boolean naoExiste = repository.existsByNoUsuarioAndDataExclusaoIsNull("admin123");
        //verificação
        Assertions.assertFalse(naoExiste);
    }

    @Test
    public void deveRetornarFalsoQuandoTiverLoginExcluidoNaBaseEBuscarOLoginPeloNomeDeUsuario(){
        //cenário

        login.setDataExclusao(LocalDateTime.now());
        entityManager.persist(login);
        //ação
        Optional<Login> loginDoBanco = repository.findByNoUsuarioAndDataExclusaoIsNull("Admin");
        //verificação
        Assertions.assertFalse(loginDoBanco.isPresent());
    }

    @Test
    public void deveRetornarFalsoQuandoTiverLoginExcluidoNaBaseEVerificarSeExisteLoginNaBase(){
        //cenário

        login.setDataExclusao(LocalDateTime.now());
        entityManager.persist(login);
        //ação
        boolean naoExiste = repository.existsByNoUsuarioAndDataExclusaoIsNull("Admin");
        //verificação
        Assertions.assertFalse(naoExiste);
    }

    @Test
    public void deveBuscarLoginComBaseNoNomeDeUsuario(){
        //cenário

        entityManager.persist(login);
        //ação
        Optional<Login> loginDoBanco = repository.findByNoUsuarioAndDataExclusaoIsNull("Admin");
        //verificação
        Assertions.assertTrue(loginDoBanco.isPresent());
    }

    @Test
    public void deveRetornarLoginVazioComBaseNoNomeDeUsuario(){
        //cenário

        //ação
        Optional<Login> loginDoBanco = repository.findByNoUsuarioAndDataExclusaoIsNull("Admin");
        //verificação
        Assertions.assertFalse(loginDoBanco.isPresent());
    }*/
}
