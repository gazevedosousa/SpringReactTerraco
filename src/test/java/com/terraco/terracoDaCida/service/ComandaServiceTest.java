package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.ClienteRepository;
import com.terraco.terracoDaCida.model.repository.ComandaRepository;
import com.terraco.terracoDaCida.service.impl.ComandaServiceImpl;
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
public class ComandaServiceTest {

    @MockBean
    ComandaRepository repository;
    @MockBean
    ClienteRepository clienteRepository;
    @SpyBean
    ComandaServiceImpl service;

    @Spy
    private ComandaMapper mapper = Mappers.getMapper(ComandaMapper.class);
    @Spy
    private ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);


   /* @Test(expected = Test.None.class)
    public void deveValidarClienteComSucesso(){
        //cenário
        Comanda comanda = criaCenario();
        Mockito.when(clienteRepository.existsByNoClienteAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(true);
        //validação
        service.verificaCliente(comanda.getCliente().getNoCliente());
    }
    @Test
    public void deveLancarErroAoValidarCliente(){
        //cenário
        Comanda comanda = criaCenario();
        Mockito.when(clienteRepository.existsByNoClienteAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(false);
        //ação
        ElementoNaoEncontradoException erroComandaService = Assertions.assertThrows(ElementoNaoEncontradoException.class,
                () -> service.verificaCliente(comanda.getCliente().getNoCliente()));
        //validação
        Assertions.assertEquals("Erro ao criar comanda. Cliente não existe no Banco de Dados", erroComandaService.getMessage());
    }*/

    @Test(expected = Test.None.class)
    public void deveCriarComandaComSucesso(){
        //cenário
        Mockito.when(clienteRepository.existsByNoClienteAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(true);
        Comanda comanda = criaCenario();
        Mockito.when(repository.save(comanda)).thenReturn(comanda);
        //ação
        ComandaDTOView comandaCriada = service.criar(comanda);
        //verificação
        Assertions.assertNotNull(comandaCriada);
        Assertions.assertEquals(comandaCriada.getId(), comanda.getId());
        Assertions.assertEquals(comandaCriada.getSituacaoComanda(), comanda.getSituacaoComanda().toString());

    }

/*    @Test(expected = ElementoNaoEncontradoException.class)
    public void naoDeveCriarComanda(){
        //cenário
        Comanda comanda = criaCenario();
        Mockito.doThrow(ElementoNaoEncontradoException.class).when(service).verificaCliente(comanda.getCliente().getNoCliente());
        //ação
        service.criar(comanda);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(comanda);
    }*/

    @Test(expected = Test.None.class)
    public void deveAlterarSituacaoDaComandaParaPagaComSucesso() {
        //cenário
        Comanda comanda = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(comanda));
        //ação
        service.fecharComanda(comanda);
        //verificação
        Assertions.assertEquals(comanda.getSituacaoComanda(), SituacaoComandaEnum.PAGA);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void naoDeveAlterarSituacaoDaComanda() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        service.deletar(new Comanda());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new Comanda());
    }

    @Test(expected = Test.None.class)
    public void deveDeletarComandaComSucesso() {
        //cenário
        Comanda comanda = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(comanda));
        //ação
        service.deletar(comanda);
        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(comanda);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void naoDeveDeletarComanda() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        service.deletar(new Comanda());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new Comanda());
    }

    @Test(expected = Test.None.class)
    public void deveBuscarComandaComSucesso() {
        //cenário
        Comanda comanda = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(comanda));
        //ação
        Comanda comandaEncontrada = service.buscarComanda(Mockito.anyLong());
        //verificação
        Assertions.assertNotNull(comandaEncontrada);
    }

    @Test
    public void deveEnviarErroAoProcurarComanda() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ElementoNaoEncontradoException erroComanda = Assertions.assertThrows(ElementoNaoEncontradoException.class,
                () -> service.buscarComanda(Mockito.anyLong()));
        //verificação
        Assertions.assertEquals("Comanda não encontrada na Base de Dados", erroComanda.getMessage());
    }


    @Test(expected = Test.None.class)
    public void deveBuscarTodasAsComandasComSucesso() {
        //cenário
        Comanda comanda = criaCenario();
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(List.of(comanda));
        //ação
        List<ComandaDTOView> comandasEncontradas = service.buscarTodasAsComandas();
        //verificação
        Assertions.assertNotNull(comandasEncontradas);
    }

    @Test
    public void naoDeveBuscarNenhumaComandaProduto() {
        //cenário
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(Collections.emptyList());
        //ação
        List<ComandaDTOView> comandasEncontradas = service.buscarTodasAsComandas();
        //verificação
        Assertions.assertTrue(comandasEncontradas.isEmpty());
    }

    private Comanda criaCenario(){

        Cliente cliente = criaCliente();
        Mockito.when(clienteRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(cliente));

        ComandaDTO dto =ComandaDTO.builder()
                .idCliente(cliente.getId())
                .situacaoComanda("ABERTA")
                .build();

        Comanda comandaMapeada = mapper.toEntity(dto);
        comandaMapeada.setId(1L);
        comandaMapeada.setDataCriacao(LocalDateTime.now());
        comandaMapeada.setDataAtualizacao(LocalDateTime.now());

        return comandaMapeada;
    }

    private Cliente criaCliente(){
        ClienteDTO dto =ClienteDTO.builder()
                .noCliente("Cliente")
                .celCliente("31985467124")
                .emailCliente("cliente@cliente.com")
                .build();

        Cliente clienteMapeado = clienteMapper.toEntity(dto);
        clienteMapeado.setId(1L);
        clienteMapeado.setDataCriacao(LocalDateTime.now());
        clienteMapeado.setDataAtualizacao(LocalDateTime.now());

        return clienteMapeado;
    }


}
