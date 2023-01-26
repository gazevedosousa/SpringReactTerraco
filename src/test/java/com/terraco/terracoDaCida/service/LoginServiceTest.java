package com.terraco.terracoDaCida.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LoginServiceTest {
    /*@Mock
    LoginRepository repository;
    @InjectMocks
    LoginServiceImpl service;

    @Test(expected = Test.None.class)
    public void deveValidarLoginComSucesso(){
        //cenário
        Mockito.when(repository.existsByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(false);
        //validação
        service.validarLogin("Admin");
    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancarErroAoValidarLogin(){
        //cenário
        Mockito.when(repository.existsByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(true);
        //validação
        service.validarLogin("Admin");
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarLoginComSucesso() throws NoSuchAlgorithmException {
        //cenário
        String noUsuario = "Admin";
        String coSenha = "Admin";

        Login login = criaLogin();
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(noUsuario)).thenReturn(Optional.of(login));

        //ação
        Login resultado = service.autenticar(noUsuario, coSenha);

        //verificação
        Assertions.assertNotNull(resultado);
    }

    @Test
    public void deveLancarErroAutenticacaoQuandoNaoEncontrarLogin(){
        //cenário
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.empty());
        //ação
        ErroAutenticacao exception = Assertions.assertThrows(ErroAutenticacao.class, () -> service.autenticar("Admin", "Admin"));
        //validação
        Assertions.assertEquals("Usuário não encontrado na base de dados", exception.getMessage());
    }

    @Test
    public void deveLancarErroAutenticacaoQuandoSenhaForDiferente(){
        //cenário
        String coSenha = "Admin";
        TipoLogin tipoLogin = criaTipoLogin();

        Login login = Login.builder()
                      .noUsuario("Admin")
                      .tipoLogin(tipoLogin)
                      .coSenha(coSenha)
                      .dataCriacao(LocalDateTime.now())
                      .dataAtualizacao(LocalDateTime.now())
                      .build();

        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        //ação
        ErroAutenticacao exception = Assertions.assertThrows(ErroAutenticacao.class, () -> service.autenticar("Admin", "senhaInvalida"));
        //validação
        Assertions.assertEquals("Senha inválida", exception.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveCriarLoginComSucesso(){
        //cenário
        Mockito.doNothing().when(service).validarLogin(Mockito.anyString());
        Login login = criaLogin();
        Mockito.when(repository.save(Mockito.any(Login.class))).thenReturn(login);
        //ação
        Login loginCriado = service.criarLogin(new Login());
        //verificação
        Assertions.assertNotNull(loginCriado);
        Assertions.assertEquals(loginCriado.getId(), 1L);
        Assertions.assertEquals(loginCriado.getNoUsuario(), "Admin");
        Assertions.assertEquals(loginCriado.getCoSenha(), "Admin");
        Assertions.assertEquals(loginCriado.getTipoLogin().getId(), 1L);
        Assertions.assertEquals(loginCriado.getDataCriacao(), LocalDateTime.now());
        Assertions.assertEquals(loginCriado.getDataAtualizacao(), LocalDateTime.now());
        Assertions.assertNull(loginCriado.getDataExclusao());
    }

    @Test(expected = RegraNegocioException.class)
    public void naoDeveCriarLoginComNoUsuarioJaExistente(){
        //cenário
        Login login = criaLogin();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarLogin("Admin");
        Mockito.when(repository.save(Mockito.any(Login.class))).thenReturn(login);
        //ação
        service.criarLogin(login);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(login);
    }

    @Test(expected = Test.None.class)
    public void deveAtualizarSenhaDeLoginExistente(){
        //cenário
        Login login = criaLogin();
        String senhaAlterada = "senhaAlterada";
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        Mockito.when(repository.save(Mockito.any(Login.class))).thenReturn(login);
        //ação
        Login loginAtualizado = service.alterarSenha(login, senhaAlterada);
        //verificação
        Assertions.assertEquals(senhaAlterada, loginAtualizado.getCoSenha());
    }

    @Test(expected = ErroAtualizacaoLogin.class)
    public void naoDeveAtualizarSenhaDeLoginInexistente(){
        //cenário
        Login login = criaLogin();
        String senhaAlterada = "senhaAlterada";
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        Mockito.when(repository.save(Mockito.any(Login.class))).thenReturn(login);
        //ação
        service.alterarSenha(new Login(), senhaAlterada);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(login);
    }

    @Test(expected = Test.None.class)
    public void deveDeletarLoginExistente(){
        //cenário
        Login login = criaLogin();
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        Mockito.when(repository.save(Mockito.any(Login.class))).thenReturn(login);
        //ação
        Login loginExlcuido = service.deletarLogin(login);
        //verificação
        Assertions.assertNotNull(loginExlcuido.getDataExclusao());
    }

    @Test(expected = ErroExclusaoLogin.class)
    public void naoDeveExcluirLoginInexistente(){
        //cenário
        Login login = criaLogin();
        Mockito.when(repository.findByNoUsuarioAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(Optional.of(login));
        Mockito.when(repository.save(Mockito.any(Login.class))).thenReturn(login);
        //ação
        service.deletarLogin(new Login());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(login);
    }

    public static TipoLogin criaTipoLogin(){

        return TipoLogin.builder()
                .id(1L)
                .noTipoLogin("Administrador")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();
    }

    public static Login criaLogin(){
        TipoLogin tipoLogin = criaTipoLogin();

        return Login.builder()
                .id(1L)
                .noUsuario("Admin")
                .coSenha("Admin")
                .tipoLogin(tipoLogin)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();
    }*/

}
