package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.api.dto.LoginDTO;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.LoginMapper;
import com.terraco.terracoDaCida.model.entity.Login;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")

public class LoginRepositoryTest {
    @Autowired
    LoginRepository repository;
    @Autowired
    TestEntityManager entityManager;
    @Spy
    private LoginMapper mapper = Mappers.getMapper(LoginMapper.class);

    @Test
    public void deveVerificarAExistenciaDeUmLoginComBaseNoNomeDoUsuario(){
        //cenário
        entityManager.persist(criaLogin());
        //ação
        boolean existe = repository.existsByNoUsuarioAndDataExclusaoIsNull("User");
        //verificação
        Assertions.assertTrue(existe);
    }

    @Test
    public void deveRetornarFalseQuandoNaoExisteNenhumLoginNoBanco(){
        //cenário

        //ação
        boolean existe = repository.existsByNoUsuarioAndDataExclusaoIsNull("User");
        //verificação
        Assertions.assertFalse(existe);
    }

    @Test
    public void deveAcharTodosOsLoginsQueNaoEstaoExcluidos(){
        //cenário
        Login login1 = entityManager.persist(criaLogin());
        Login login2 = criaLogin();
        login2.setNoUsuario("usuario2");
        entityManager.persist(login2);
        Login login3 = criaLogin();
        login3.setNoUsuario("usuario3");
        login3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(login3);

        List<Login> loginNaoExcluido = new ArrayList<>();
        loginNaoExcluido.add(login1);
        loginNaoExcluido.add(login2);
        //ação
        List<Login> loginList = repository.findAllWhereDataExclusaoIsNull();
        //verificação
        Assertions.assertEquals(loginNaoExcluido, loginList);
    }

    @Test
    public void deveRetornarUmUsuarioQuandoAchadoNoBanco(){
        //cenário
        entityManager.persist(criaLogin());
        //ação
        Login loginDoBanco = repository.findByNoUsuarioAndDataExclusaoIsNull("User")
                .orElseThrow(() -> new ElementoNaoEncontradoException("Usuário não Encontrado") );
        //verificação
        Assertions.assertNotNull(loginDoBanco);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveRetornarErroAoNaoAcharUsuario(){
        //verificação
        Login loginDoBanco = repository.findByNoUsuarioAndDataExclusaoIsNull("User")
                .orElseThrow(() -> new ElementoNaoEncontradoException("Usuário não Encontrado") );
    }

    @Test
    public void deveRetornarUmUsuarioComBaseNoIdAchadoNoBanco(){
        //cenário
        Login loginPersistido = entityManager.persist(criaLogin());
        //ação
        Login loginDoBanco = repository.findByIdAndDataExclusaoIsNull(loginPersistido.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Usuário não Encontrado") );
        //verificação
        Assertions.assertNotNull(loginDoBanco);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveRetornarErroAoNaoAcharUsuarioComBaseNoId(){
        //verificação
        Login loginDoBanco = repository.findByIdAndDataExclusaoIsNull(1l)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Usuário não Encontrado") );
    }

    private Login criaLogin(){
        LoginDTO dto =LoginDTO.builder()
            .noUsuario("User")
            .perfil("USER")
            .coSenha("senha")
            .build();

        Login loginMapeado = mapper.toEntity(dto);
        loginMapeado.setDataCriacao(LocalDateTime.now());
        loginMapeado.setDataAtualizacao(LocalDateTime.now());

        return loginMapeado;
    }

}
