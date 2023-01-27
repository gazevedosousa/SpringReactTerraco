package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ClienteDTOView;
import com.terraco.terracoDaCida.exceptions.ErroClienteService;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.repository.ClienteRepository;
import com.terraco.terracoDaCida.service.impl.ClienteServiceImpl;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ClienteServiceTest {

    @MockBean
    ClienteRepository repository;
    @SpyBean
    ClienteServiceImpl service;
    @Spy
    private ClienteMapper mapper = Mappers.getMapper(ClienteMapper.class);

    @Test(expected = Test.None.class)
    public void deveValidarClienteComSucesso(){
        //cenário
        Mockito.when(repository.existsByNoClienteAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(false);
        //validação
        service.validarCliente("Cliente");
    }
    @Test(expected = ErroClienteService.class)
    public void deveLancarErroAoValidar(){
        //cenário
        Mockito.when(repository.existsByNoClienteAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(true);
        //validação
        service.validarCliente("Cliente");
    }

    @Test(expected = Test.None.class)
    public void deveCriarClienteComSucesso(){
        //cenário
        Mockito.doNothing().when(service).validarCliente(Mockito.anyString());
        Cliente cliente = criaCliente();
        Mockito.when(repository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        //ação
        ClienteDTOView clienteCriado = service.criar(new Cliente());
        //verificação
        Assertions.assertNotNull(clienteCriado);
        Assertions.assertEquals(clienteCriado.getId(), cliente.getId());
        Assertions.assertEquals(clienteCriado.getNoCliente(), cliente.getNoCliente());
        Assertions.assertEquals(clienteCriado.getEmailCliente(), cliente.getEmailCliente());
        Assertions.assertEquals(clienteCriado.getCelCliente(), cliente.getCelCliente());

    }

    @Test(expected = ErroClienteService.class)
    public void naoDeveCriarCliente(){
        //cenário
        Mockito.doThrow(ErroClienteService.class).when(service).validarCliente(Mockito.anyString());
        Cliente cliente = criaCliente();
        //ação
        service.criar(cliente);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(cliente);
    }

    @Test(expected = Test.None.class)
    public void deveAlterarApenasEmailComSucesso() {
        //cenário
        Cliente cliente = criaCliente();
        String novoCel = "";
        String novoEmail = "novoemail@cliente.com";
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(cliente));
        //ação
        service.atualizar(cliente, novoCel, novoEmail);
        //verificação
        Assertions.assertNotEquals(novoCel, cliente.getCelCliente());
        Assertions.assertEquals(novoEmail, cliente.getEmailCliente());

    }

    @Test(expected = Test.None.class)
    public void deveAlterarApenasCelComSucesso() {
        //cenário
        Cliente cliente = criaCliente();
        String novoCel = "31998587464";
        String novoEmail = "";
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(cliente));
        //ação
        service.atualizar(cliente, novoCel, novoEmail);
        //verificação
        Assertions.assertEquals(novoCel, cliente.getCelCliente());
        Assertions.assertNotEquals(novoEmail, cliente.getEmailCliente());

    }

    @Test(expected = Test.None.class)
    public void deveAlterarCelEEmailComSucesso() {
        //cenário
        Cliente cliente = criaCliente();
        String novoCel = "31998587464";
        String novoEmail = "novoemail@cliente.com";
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(cliente));
        //ação
        service.atualizar(cliente, novoCel, novoEmail);
        //verificação

        Assertions.assertEquals(novoCel, cliente.getCelCliente());
        Assertions.assertEquals(novoEmail, cliente.getEmailCliente());
    }

    @Test
    public void deveEnviarErroAoAtualizarCliente() {
        //cenário
        Cliente cliente = criaCliente();
        String novoCel = "31998587464";
        String novoEmail = "novoemail@cliente.com";
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ErroClienteService erroClienteService = Assertions.assertThrows(ErroClienteService.class,
                () -> service.atualizar(cliente, novoCel, novoEmail));
        //verificação
        Assertions.assertEquals("Cliente não encontrado no Banco de Dados", erroClienteService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveDeletarClienteComSucesso() {
        //cenário
        Cliente cliente = criaCliente();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(cliente));
        //ação
        service.deletar(cliente);
        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(cliente);
    }

    @Test(expected = ErroClienteService.class)
    public void naoDeveDeletarCliente() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        service.deletar(new Cliente());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new Cliente());
    }

    @Test(expected = Test.None.class)
    public void deveBuscarClienteComSucesso() {
        //cenário
        Cliente cliente = criaCliente();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(cliente));
        //ação
        Cliente clienteEncontrado = service.buscarCliente(Mockito.anyLong());
        //verificação
        Assertions.assertNotNull(clienteEncontrado);
    }

    @Test
    public void naoDeveBuscarCliente() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ErroClienteService erroClienteService = Assertions.assertThrows(ErroClienteService.class,
                () -> service.buscarCliente(Mockito.anyLong()));
        //verificação
        Assertions.assertEquals("Cliente não encontrado no Banco de Dados", erroClienteService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveAcharTodosOsClientesComSucesso() {
        //cenário
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(List.of(new Cliente()));
        //ação
        List<ClienteDTOView> clientesEncontrados = service.buscarTodosOsClientes();
        //verificação
        Assertions.assertNotNull(clientesEncontrados);
    }

    @Test
    public void naoDeveBuscarNenhumCliente() {
        //cenário
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(Collections.emptyList());
        //ação
        List<ClienteDTOView> clientesEncontrados = service.buscarTodosOsClientes();
        //verificação
        Assertions.assertTrue(clientesEncontrados.isEmpty());
    }

    private Cliente criaCliente(){
        ClienteDTO dto =ClienteDTO.builder()
                .noCliente("Cliente")
                .emailCliente("cliente@cliente.com")
                .celCliente("31993819898")
                .build();

        Cliente clienteMapeado = mapper.toEntity(dto);
        clienteMapeado.setId(1L);
        clienteMapeado.setDataCriacao(LocalDateTime.now());
        clienteMapeado.setDataAtualizacao(LocalDateTime.now());

        return clienteMapeado;
    }

}
