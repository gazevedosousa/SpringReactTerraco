package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.LoginDTO;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.exceptions.ErroLoginService;
import com.terraco.terracoDaCida.mapper.LoginMapper;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.repository.LoginRepository;
import com.terraco.terracoDaCida.service.impl.LoginServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LoginServiceTest {

    @MockBean
    LoginRepository repository;
    @SpyBean
    LoginServiceImpl service;
    @Spy
    private LoginMapper mapper = Mappers.getMapper(LoginMapper.class);

    @Test(expected = Test.None.class)
    public void deveValidarLoginComSucesso(){
        //cenário
        Mockito.when(repository.existsByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(false);
        //validação
        service.validarLogin("Login");
    }
    @Test(expected = ErroLoginService.class)
    public void deveLancarErroAoValidar(){
        //cenário
        Mockito.when(repository.existsByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(true);
        //validação
        service.validarLogin("Login");
    }
    @Test(expected = Test.None.class)
    public void deveAutenticarLogin() throws NoSuchAlgorithmException {
        //cenário
        Login login = criaLogin();
        String noUsuario = "User";
        String coSenha = "senha";
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        //ação
        Login loginAutenticado = service.autenticar(noUsuario, coSenha);
        //verificação
        Assertions.assertNotNull(loginAutenticado);
    }

    @Test
    public void naoDeveAutenticarLoginPorLoginInexistente(){
        //cenário
        String noUsuario = "User";
        String coSenha = "senha";
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.empty());
        //ação
        ErroLoginService erroLoginService = Assertions.assertThrows(ErroLoginService.class,
                () -> service.autenticar(noUsuario, coSenha));
        //verificação
        Assertions.assertEquals("Usuário não encontrado na base de dados", erroLoginService.getMessage());
    }

    @Test
    public void naoDeveAutenticarLoginPorSenhaIncorreta() {
        //cenário
        Login login = criaLogin();
        String noUsuario = "User";
        String coSenha = "senhaErrada";
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        //ação
        ErroLoginService erroLoginService = Assertions.assertThrows(ErroLoginService.class,
                () -> service.autenticar(noUsuario, coSenha));
        //verificação
        Assertions.assertEquals("Senha inválida", erroLoginService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveCriarLoginComSucesso(){
        //cenário
        Mockito.doNothing().when(service).validarLogin(Mockito.anyString());
        Login login = criaLogin();
        Mockito.when(repository.save(Mockito.any(Login.class))).thenReturn(login);
        //ação
        LoginDTOView loginCriado = service.criarLogin(new Login());
        //verificação
        Assertions.assertNotNull(loginCriado);
        Assertions.assertEquals(loginCriado.getId(), login.getId());
        Assertions.assertEquals(loginCriado.getNoUsuario(), login.getNoUsuario());
        Assertions.assertEquals(loginCriado.getPerfil(), login.getPerfil().toString());

    }

    @Test(expected = ErroLoginService.class)
    public void naoDeveCriarLogin(){
        //cenário
        Mockito.doThrow(ErroLoginService.class).when(service).validarLogin(Mockito.anyString());
        Login login = criaLogin();
        //ação
        service.criarLogin(login);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(login);
    }

    @Test(expected = Test.None.class)
    public void deveAlterarLoginComSucesso() throws NoSuchAlgorithmException {
        //cenário
        Login login = criaLogin();
        String novaSenha = "novaSenha";
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        //ação
        LoginDTOView loginAtualizado = service.alterarSenha(login, novaSenha);
        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(login);
    }

    @Test(expected = ErroLoginService.class)
    public void naoDeveAlterarLogin() throws NoSuchAlgorithmException {
        //cenário
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.empty());
        //ação
        service.alterarSenha(new Login(), "senha");
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new Login());
    }

    @Test(expected = Test.None.class)
    public void deveDeletarLoginComSucesso() {
        //cenário
        Login login = criaLogin();
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        //ação
        service.deletarLogin(login);
        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(login);
    }

    @Test(expected = ErroLoginService.class)
    public void naoDeveDeletarLogin() {
        //cenário
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.empty());
        //ação
        service.deletarLogin(new Login());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new Login());
    }

    @Test(expected = Test.None.class)
    public void deveBuscarLoginComSucesso() {
        //cenário
        Login login = criaLogin();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(login));
        //ação
        Login loginEncontrado = service.buscarLogin(Mockito.anyLong());
        //verificação
        Assertions.assertNotNull(loginEncontrado);
    }

    @Test
    public void naoDeveBuscarLogin() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ErroLoginService erroLoginService = Assertions.assertThrows(ErroLoginService.class,
                () -> service.buscarLogin(Mockito.anyLong()));
        //verificação
        Assertions.assertEquals("Usuário não encontrado na base de dados", erroLoginService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveAcharTodosOsLoginsComSucesso() {
        //cenário
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(List.of(new Login()));
        //ação
        List<LoginDTOView> loginsEncontrados = service.buscarTodosOsLogins();
        //verificação
        Assertions.assertNotNull(loginsEncontrados);
    }

    @Test
    public void naoDeveBuscarNenhumLogin() {
        //cenário
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(Collections.emptyList());
        //ação
        List<LoginDTOView> loginsEncontrados = service.buscarTodosOsLogins();
        //verificação
        Assertions.assertTrue(loginsEncontrados.isEmpty());
    }

    private Login criaLogin(){
        LoginDTO dto =LoginDTO.builder()
                .noUsuario("User")
                .perfil("USER")
                .coSenha("senha")
                .build();

        Login loginMapeado = mapper.toEntity(dto);
        loginMapeado.setId(1L);
        loginMapeado.setDataCriacao(LocalDateTime.now());
        loginMapeado.setDataAtualizacao(LocalDateTime.now());

        return loginMapeado;
    }

}
