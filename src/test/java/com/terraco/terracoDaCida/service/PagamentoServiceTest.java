package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.PagamentoDTO;
import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.mapper.PagamentoMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Pagamento;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.ClienteRepository;
import com.terraco.terracoDaCida.model.repository.ComandaRepository;
import com.terraco.terracoDaCida.model.repository.PagamentoRepository;
import com.terraco.terracoDaCida.service.impl.PagamentoServiceImpl;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PagamentoServiceTest {

    @MockBean
    PagamentoRepository repository;
    @MockBean
    ComandaRepository comandaRepository;
    @MockBean
    ClienteRepository clienteRepository;
    @SpyBean
    PagamentoServiceImpl service;

    @Spy
    private PagamentoMapper mapper = Mappers.getMapper(PagamentoMapper.class);
    @Spy
    private ComandaMapper comandaMapper = Mappers.getMapper(ComandaMapper.class);
    @Spy
    private ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);


    @Test(expected = Test.None.class)
    public void deveValidarComandaComSucesso(){
        //cenário

        Pagamento pagamento = criaCenario();
        //validação
        service.validarPagamento(pagamento.getComanda());
    }
    @Test
    public void naoDeveValidarComanda(){
        //cenário
        Pagamento pagamento = criaCenario();
        pagamento.getComanda().setSituacaoComanda(SituacaoComandaEnum.PAGA);
        //ação
        ElementoNaoEncontradoException erroPagamentoService = Assertions.assertThrows(ElementoNaoEncontradoException.class,
                () -> service.validarPagamento(pagamento.getComanda()));
        //validação
        Assertions.assertEquals("Não é possível efetuar pagamento. Comanda já paga anteriormente.", erroPagamentoService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveCriarPagamentoComSucesso(){
        //cenário
        Pagamento pagamento = criaCenario();
        Mockito.when(repository.save(pagamento)).thenReturn(pagamento);
        //ação
        PagamentoDTOView pagamentoCriado = service.pagarParcial(pagamento);
        //verificação
        Assertions.assertNotNull(pagamentoCriado);
        Assertions.assertEquals(pagamentoCriado.getId(), pagamento.getId());
        Assertions.assertEquals(pagamentoCriado.getTipoPagamento(), "DEBITO");
        Assertions.assertEquals(pagamentoCriado.getVrPagamento(), BigDecimal.TEN);
        Assertions.assertEquals(pagamentoCriado.getNoCliente(), pagamento.getComanda().getCliente().getNoCliente());

    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void naoDeveCriarPagamento(){
        //cenário
        Pagamento pagamento = criaCenario();
        Mockito.doThrow(ElementoNaoEncontradoException.class).when(service).validarPagamento(pagamento.getComanda());
        //ação
        service.pagarParcial(pagamento);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(pagamento);
    }

    @Test(expected = Test.None.class)
    public void deveDeletarPagamentoComSucesso() {
        //cenário
        Pagamento pagamento = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(pagamento));
        //ação
        service.estornarPagamento(pagamento);
        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(pagamento);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void naoDeveDeletarPagamento() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        service.estornarPagamento(new Pagamento());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new Pagamento());
    }

    @Test(expected = Test.None.class)
    public void deveBuscarPagamentoComSucesso() {
        //cenário
        Pagamento pagamento = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(pagamento));
        //ação
        Pagamento pagamentoEncontrado = service.buscarPagamento(Mockito.anyLong());
        //verificação
        Assertions.assertNotNull(pagamentoEncontrado);
    }

    @Test
    public void deveEnviarErroAoProcurarPagamento() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ElementoNaoEncontradoException erroPagamentoService = Assertions.assertThrows(ElementoNaoEncontradoException.class,
                () -> service.buscarPagamento(Mockito.anyLong()));
        //verificação
        Assertions.assertEquals("Pagamento não encontrado na Base de Dados", erroPagamentoService.getMessage());
    }


    @Test(expected = Test.None.class)
    public void deveBuscarTodosOsPagamentosDeUmaComanda() {
        //cenário
        Pagamento pagamento = criaCenario();
        Mockito.when(repository.findByComandaIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(List.of(pagamento));
        //ação
        List<PagamentoDTOView> produtosNaComandaEncontrados = service.buscarPagamentosDeUmaComanda(pagamento.getComanda().getId());
        //verificação
        Assertions.assertNotNull(produtosNaComandaEncontrados);
    }

    @Test
    public void naoDeveBuscarNenhumPagamento() {
        //cenário
        Mockito.when(repository.findByComandaIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Collections.emptyList());
        //ação
        List<PagamentoDTOView> comandasEncontradas = service.buscarPagamentosDeUmaComanda(Mockito.anyLong());
        //verificação
        Assertions.assertTrue(comandasEncontradas.isEmpty());
    }

    private Pagamento criaCenario(){
        Mockito.when(clienteRepository.existsByNoClienteAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(true);
        Cliente cliente = criaCliente();
        Mockito.when(clienteRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(cliente));
        Comanda comanda = criaComanda(cliente.getId());
        Mockito.when(comandaRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(comanda));

        PagamentoDTO dto = PagamentoDTO.builder()
                .idComanda(comanda.getId())
                .tipoPagamento("DEBITO")
                .vrPagamento(BigDecimal.TEN)
                .build();

        Pagamento pagamentoMapeado = mapper.toEntity(dto);
        pagamentoMapeado.setId(1L);
        pagamentoMapeado.setDataCriacao(LocalDateTime.now());
        pagamentoMapeado.setDataAtualizacao(LocalDateTime.now());

        return pagamentoMapeado;

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

    private Comanda criaComanda(Long idCliente){
        ComandaDTO dto =ComandaDTO.builder()
                .idCliente(idCliente)
                .situacaoComanda("ABERTA")
                .build();

        Comanda comandaMapeada = comandaMapper.toEntity(dto);
        comandaMapeada.setId(1L);
        comandaMapeada.setDataCriacao(LocalDateTime.now());
        comandaMapeada.setDataAtualizacao(LocalDateTime.now());

        return comandaMapeada;
    }

}
