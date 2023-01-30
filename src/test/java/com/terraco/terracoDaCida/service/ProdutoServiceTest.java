package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.ProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.TipoProdutoDTO;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.ProdutoMapper;
import com.terraco.terracoDaCida.mapper.TipoProdutoMapper;
import com.terraco.terracoDaCida.model.entity.Produto;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
import com.terraco.terracoDaCida.model.repository.ProdutoRepository;
import com.terraco.terracoDaCida.model.repository.TipoProdutoRepository;
import com.terraco.terracoDaCida.service.impl.ProdutoServiceImpl;
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
public class ProdutoServiceTest {

    @MockBean
    ProdutoRepository repository;
    @MockBean
    TipoProdutoRepository tipoProdutorepository;
    @SpyBean
    ProdutoServiceImpl service;
    @Spy
    private ProdutoMapper mapper = Mappers.getMapper(ProdutoMapper.class);
    @Spy
    private TipoProdutoMapper tipoProdutoMapper = Mappers.getMapper(TipoProdutoMapper.class);


    @Test(expected = Test.None.class)
    public void deveValidarProdutoComSucesso(){
        //cenário
        Mockito.when(repository.existsByNoProdutoAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(false);
        //validação
        service.validarProduto("Produto");
    }
    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveLancarErroAoValidar(){
        //cenário
        Mockito.when(repository.existsByNoProdutoAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(true);
        //validação
        service.validarProduto("Produto");
    }

    @Test(expected = Test.None.class)
    public void deveCriarProdutoComSucesso(){
        //cenário
        Mockito.doNothing().when(service).validarProduto(Mockito.anyString());
        Produto produto = criaCenario();
        Mockito.when(repository.save(Mockito.any(Produto.class))).thenReturn(produto);
        //ação
        ProdutoDTOView produtoCriado = service.criar(produto);
        //verificação
        Assertions.assertNotNull(produtoCriado);
        Assertions.assertEquals(produtoCriado.getId(), produto.getId());
        Assertions.assertEquals(produtoCriado.getNoProduto(), produto.getNoProduto());
        Assertions.assertEquals(produtoCriado.getVrProduto(), produto.getVrProduto());
        Assertions.assertEquals(produtoCriado.getTipoProduto(), produto.getTipoProduto().getNoTipoProduto());

    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void naoDeveCriarProduto(){
        //cenário
        Mockito.doThrow(ElementoNaoEncontradoException.class).when(service).validarProduto(Mockito.anyString());
        Produto produto = criaCenario();
        //ação
        service.criar(produto);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(produto);
    }

    @Test(expected = Test.None.class)
    public void deveAtualizarProdutoComSucesso() {
        //cenário
        Produto produto = criaCenario();
        BigDecimal novoVrProduto = BigDecimal.ONE;
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(produto));
        //ação
        service.atualizar(produto, novoVrProduto);
        //verificação
        Assertions.assertEquals(novoVrProduto, produto.getVrProduto());

    }

    @Test
    public void deveEnviarErroAoAtualizarProduto() {
        //cenário
        Produto produto = criaCenario();
        BigDecimal novoVrProduto = BigDecimal.ONE;
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ElementoNaoEncontradoException erroProdutoService = Assertions.assertThrows(ElementoNaoEncontradoException.class,
                () -> service.atualizar(produto, novoVrProduto));
        //verificação
        Assertions.assertEquals("Produto não encontrado na Base de Dados", erroProdutoService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveDeletarProdutoComSucesso() {
        //cenário
        Produto produto = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(produto));
        //ação
        service.deletar(produto);
        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(produto);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void naoDeveDeletarProduto() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        service.deletar(new Produto());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new Produto());
    }

    @Test(expected = Test.None.class)
    public void deveBuscarProdutoComSucesso() {
        //cenário
        Produto produto = criaCenario();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(produto));
        //ação
        Produto produtoEncontrado = service.buscarProdutoNaoExcluido(Mockito.anyLong());
        //verificação
        Assertions.assertNotNull(produtoEncontrado);
    }

    @Test
    public void naoDeveBuscarProduto() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ElementoNaoEncontradoException erroProdutoService = Assertions.assertThrows(ElementoNaoEncontradoException.class,
                () -> service.buscarProdutoNaoExcluido(Mockito.anyLong()));
        //verificação
        Assertions.assertEquals("Produto não encontrado na Base de Dados", erroProdutoService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveAcharTodosOsProdutosComSucesso() {
        //cenário
        Produto produto = criaCenario();
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(List.of(produto));
        //ação
        List<ProdutoDTOView> produtosEncontrados = service.buscarTodosOsProdutosNaoExcluidos();
        //verificação
        Assertions.assertNotNull(produtosEncontrados);
    }

    @Test
    public void naoDeveBuscarNenhumProduto() {
        //cenário
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(Collections.emptyList());
        //ação
        List<ProdutoDTOView> produtosEncontrados = service.buscarTodosOsProdutosNaoExcluidos();
        //verificação
        Assertions.assertTrue(produtosEncontrados.isEmpty());
    }

    private Produto criaCenario(){
        TipoProduto tipoProduto = criaTipoProduto();
        Mockito.when(tipoProdutorepository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(tipoProduto));

        ProdutoDTO dto =ProdutoDTO.builder()
                .noProduto("Produto")
                .idTipoProduto(tipoProduto.getId())
                .vrProduto(BigDecimal.TEN)
                .build();

        Produto produtoMapeado = mapper.toEntity(dto);
        produtoMapeado.setId(1L);
        produtoMapeado.setDataCriacao(LocalDateTime.now());
        produtoMapeado.setDataAtualizacao(LocalDateTime.now());

        return produtoMapeado;
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

}
