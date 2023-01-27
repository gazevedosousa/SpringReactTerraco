package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.ProdutoDTO;
import com.terraco.terracoDaCida.api.dto.TipoProdutoDTO;
import com.terraco.terracoDaCida.exceptions.ErroComandaProdutoService;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.mapper.ComandaProdutoMapper;
import com.terraco.terracoDaCida.mapper.ProdutoMapper;
import com.terraco.terracoDaCida.mapper.TipoProdutoMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import com.terraco.terracoDaCida.model.entity.Produto;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.ClienteRepository;
import com.terraco.terracoDaCida.model.repository.ComandaProdutoRepository;
import com.terraco.terracoDaCida.model.repository.ComandaRepository;
import com.terraco.terracoDaCida.model.repository.ProdutoRepository;
import com.terraco.terracoDaCida.model.repository.TipoProdutoRepository;
import com.terraco.terracoDaCida.service.impl.ComandaProdutoServiceImpl;
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
public class ComandaProdutoServiceTest {

    @MockBean
    ComandaProdutoRepository repository;
    @MockBean
    ComandaRepository comandaRepository;
    @MockBean
    ClienteRepository clienteRepository;
    @MockBean
    ProdutoRepository produtoRepository;
    @MockBean
    TipoProdutoRepository tipoProdutoRepository;
    @SpyBean
    ComandaProdutoServiceImpl service;
    @Spy
    private ComandaProdutoMapper mapper = Mappers.getMapper(ComandaProdutoMapper.class);
    @Spy
    private ComandaMapper comandaMapper = Mappers.getMapper(ComandaMapper.class);
    @Spy
    private ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);
    @Spy
    private TipoProdutoMapper tipoProdutoMapper = Mappers.getMapper(TipoProdutoMapper.class);
    @Spy
    private ProdutoMapper produtoMapper = Mappers.getMapper(ProdutoMapper.class);

    @Test(expected = Test.None.class)
    public void deveValidarSituacaoComandaComSucesso(){
        //cenário
        Mockito.when(comandaRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(new Comanda()));
        Mockito.when(clienteRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(new Cliente()));
        Cliente cliente = criaCliente();
        Comanda comanda = criaComanda(cliente.getId());
        //validação
        service.verificaSituacaoComanda(comanda);
    }
    @Test
    public void deveLancarErroAoValidarSituacaoComandaPorComandaPendente(){
        //cenário
        Mockito.when(comandaRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(new Comanda()));
        Mockito.when(clienteRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(new Cliente()));
        Cliente cliente = criaCliente();
        Comanda comanda = criaComanda(cliente.getId());
        comanda.setSituacaoComanda(SituacaoComandaEnum.PENDENTE);
        //ação
        ErroComandaProdutoService erroComandaProdutoService = Assertions.assertThrows(ErroComandaProdutoService.class,
                () -> service.verificaSituacaoComanda(comanda));
        //validação
        Assertions.assertEquals("Não é possível lançar produto. Comanda PENDENTE.", erroComandaProdutoService.getMessage());
    }

    @Test
    public void deveLancarErroAoValidarSituacaoComandaPorComandaPaga(){
        //cenário
        Mockito.when(comandaRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(new Comanda()));
        Mockito.when(clienteRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(new Cliente()));
        Cliente cliente = criaCliente();
        Comanda comanda = criaComanda(cliente.getId());
        comanda.setSituacaoComanda(SituacaoComandaEnum.PAGA);
        //ação
        ErroComandaProdutoService erroComandaProdutoService = Assertions.assertThrows(ErroComandaProdutoService.class,
                () -> service.verificaSituacaoComanda(comanda));
        //validação
        Assertions.assertEquals("Não é possível lançar produto. Comanda PAGA.", erroComandaProdutoService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveCriarComandaProdutoComSucesso(){
        //cenário
        ComandaProduto comandaProduto = criaCenario();
        Mockito.when(repository.save(comandaProduto)).thenReturn(comandaProduto);
        //ação
        ComandaProdutoDTOView comandaProdutoCriada = service.criar(comandaProduto);
        //verificação
        Assertions.assertNotNull(comandaProdutoCriada);
        Assertions.assertEquals(comandaProdutoCriada.getId(), comandaProduto.getId());
        Assertions.assertEquals(comandaProdutoCriada.getIdComanda(), comandaProduto.getComanda().getId());
        Assertions.assertEquals(comandaProdutoCriada.getIdProduto(), comandaProduto.getProduto().getId());

    }

    @Test(expected = ErroComandaProdutoService.class)
    public void naoDeveCriarComandaProduto(){
        //cenário
        ComandaProduto comandaProduto = criaCenario();
        Mockito.doThrow(ErroComandaProdutoService.class).when(service).verificaSituacaoComanda(comandaProduto.getComanda());
        //ação
        service.criar(comandaProduto);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(comandaProduto);
    }

    @Test(expected = Test.None.class)
    public void deveDeletarComandaProdutoComSucesso() {
        //cenário
        ComandaProduto comandaProduto = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(comandaProduto));
        //ação
        service.deletar(comandaProduto);
        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(comandaProduto);
    }

    @Test(expected = ErroComandaProdutoService.class)
    public void naoDeveDeletarComandaProduto() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        service.deletar(new ComandaProduto());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new ComandaProduto());
    }

    @Test(expected = Test.None.class)
    public void deveBuscarClienteComSucesso() {
        //cenário
        ComandaProduto comandaProduto = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(comandaProduto));
        //ação
        ComandaProduto comandaProdutoEncontrada = service.buscarComandaProduto(Mockito.anyLong());
        //verificação
        Assertions.assertNotNull(comandaProdutoEncontrada);
    }

    @Test
    public void deveEnviarErroAoProcurarComandaProduto() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ErroComandaProdutoService erroComandaProduto = Assertions.assertThrows(ErroComandaProdutoService.class,
                () -> service.buscarComandaProduto(Mockito.anyLong()));
        //verificação
        Assertions.assertEquals("Lançamento não encontrado", erroComandaProduto.getMessage());
    }


    @Test(expected = Test.None.class)
    public void deveAcharTodosOsProdutosDeUmaComanda() {
        //cenário
        ComandaProduto comandaProduto = criaCenario();
        Mockito.when(repository.findByComandaIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(List.of(comandaProduto));
        //ação
        List<ComandaProdutoDTOView> produtosNaComandaEncontrados = service.buscarProdutosDeUmaComanda(comandaProduto.getComanda().getId());
        //verificação
        Assertions.assertNotNull(produtosNaComandaEncontrados);
    }

    @Test
    public void naoDeveBuscarNenhumaComandaProduto() {
        //cenário
        Mockito.when(repository.findByComandaIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Collections.emptyList());
        //ação
        List<ComandaProdutoDTOView> produtosNaComandaEncontrados = service.buscarProdutosDeUmaComanda(Mockito.anyLong());
        //verificação
        Assertions.assertTrue(produtosNaComandaEncontrados.isEmpty());
    }

    private ComandaProduto criaCenario(){

        Cliente cliente = criaCliente();
        Mockito.when(clienteRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(cliente));
        Comanda comanda = criaComanda(cliente.getId());
        Mockito.when(comandaRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(comanda));
        TipoProduto tipoProduto = criaTipoProduto();
        Mockito.when(tipoProdutoRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(tipoProduto));
        Produto produto = criaProduto(tipoProduto.getId());
        Mockito.when(produtoRepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(produto));

        Mockito.doNothing().when(service).verificaSituacaoComanda(Mockito.any());

        ComandaProdutoDTO dto =ComandaProdutoDTO.builder()
                .idProduto(comanda.getId())
                .idComanda(produto.getId())
                .build();

        ComandaProduto comandaProdutoMapeada = mapper.toEntity(dto);
        comandaProdutoMapeada.setId(1L);
        comandaProdutoMapeada.setDataCriacao(LocalDateTime.now());
        comandaProdutoMapeada.setDataAtualizacao(LocalDateTime.now());

        return comandaProdutoMapeada;
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

    private TipoProduto criaTipoProduto(){
        TipoProdutoDTO dto =TipoProdutoDTO.builder()
                .noTipoProduto("TipoProduto")
                .build();

        TipoProduto tipoProdutoMapeado = tipoProdutoMapper.toEntity(dto);
        tipoProdutoMapeado.setId(1L);
        tipoProdutoMapeado.setDataCriacao(LocalDateTime.now());
        tipoProdutoMapeado.setDataAtualizacao(LocalDateTime.now());

        return tipoProdutoMapeado;
    }

    private Produto criaProduto(Long idTipoProduto){
        ProdutoDTO dto =ProdutoDTO.builder()
                .noProduto("Produto")
                .idTipoProduto(idTipoProduto)
                .vrProduto(BigDecimal.TEN)
                .build();

        Produto produtoMapeado = produtoMapper.toEntity(dto);
        produtoMapeado.setId(1L);
        produtoMapeado.setDataCriacao(LocalDateTime.now());
        produtoMapeado.setDataAtualizacao(LocalDateTime.now());

        return produtoMapeado;
    }



}
