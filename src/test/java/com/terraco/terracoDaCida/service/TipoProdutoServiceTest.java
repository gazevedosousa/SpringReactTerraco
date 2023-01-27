package com.terraco.terracoDaCida.service;

import com.terraco.terracoDaCida.api.dto.TipoProdutoDTO;
import com.terraco.terracoDaCida.api.dto.TipoProdutoDTOView;
import com.terraco.terracoDaCida.exceptions.ErroTipoProdutoService;
import com.terraco.terracoDaCida.mapper.TipoProdutoMapper;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
import com.terraco.terracoDaCida.model.repository.TipoProdutoRepository;
import com.terraco.terracoDaCida.service.impl.TipoProdutoServiceImpl;
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
public class TipoProdutoServiceTest {

    @MockBean
    TipoProdutoRepository repository;
    @SpyBean
    TipoProdutoServiceImpl service;
    @Spy
    private TipoProdutoMapper mapper = Mappers.getMapper(TipoProdutoMapper.class);

    @Test(expected = Test.None.class)
    public void deveValidarTipoDeProdutoComSucesso(){
        //cenário
        Mockito.when(repository.existsByNoTipoProdutoAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(false);
        //validação
        service.validarTipoProduto("TipoProduto");
    }
    @Test(expected = ErroTipoProdutoService.class)
    public void deveLancarErroAoValidar(){
        //cenário
        Mockito.when(repository.existsByNoTipoProdutoAndDataExclusaoIsNull(Mockito.anyString())).thenReturn(true);
        //validação
        service.validarTipoProduto("TipoProduto");
    }

    @Test(expected = Test.None.class)
    public void deveCriarTipoDeProdutoComSucesso(){
        //cenário
        Mockito.doNothing().when(service).validarTipoProduto(Mockito.anyString());
        TipoProduto tipoProduto = criaTipoProduto();
        Mockito.when(repository.save(Mockito.any(TipoProduto.class))).thenReturn(tipoProduto);
        //ação
        TipoProdutoDTOView tipoProdutoCriado = service.criar(tipoProduto);
        //verificação
        Assertions.assertNotNull(tipoProdutoCriado);
        Assertions.assertEquals(tipoProdutoCriado.getId(), tipoProduto.getId());
        Assertions.assertEquals(tipoProdutoCriado.getNoTipoProduto(), tipoProduto.getNoTipoProduto());

    }

    @Test(expected = ErroTipoProdutoService.class)
    public void naoDeveCriarTipoProduto(){
        //cenário
        Mockito.doThrow(ErroTipoProdutoService.class).when(service).validarTipoProduto(Mockito.anyString());
        TipoProduto tipoProduto = criaTipoProduto();
        //ação
        service.criar(tipoProduto);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(tipoProduto);
    }

    @Test(expected = Test.None.class)
    public void deveDeletarTipoProdutoComSucesso() {
        //cenário
        TipoProduto tipoProduto = criaTipoProduto();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(tipoProduto));
        //ação
        service.deletar(tipoProduto);
        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(tipoProduto);
    }

    @Test(expected = ErroTipoProdutoService.class)
    public void naoDeveDeletarTipoProduto() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        service.deletar(new TipoProduto());
        //verificação
        Mockito.verify(repository, Mockito.never()).save(new TipoProduto());
    }

    @Test(expected = Test.None.class)
    public void deveBuscarTipoProdutoComSucesso() {
        //cenário
        TipoProduto tipoProduto = criaTipoProduto();
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.of(tipoProduto));
        //ação
        TipoProduto tipoProdutoEncontrado = service.buscarTipoProduto(Mockito.anyLong());
        //verificação
        Assertions.assertNotNull(tipoProdutoEncontrado);
    }

    @Test
    public void naoDeveBuscarTipoProduto() {
        //cenário
        Mockito.when(repository.findByIdAndDataExclusaoIsNull(Mockito.anyLong())).thenReturn(Optional.empty());
        //ação
        ErroTipoProdutoService erroTipoProdutoService = Assertions.assertThrows(ErroTipoProdutoService.class,
                () -> service.buscarTipoProduto(Mockito.anyLong()));
        //verificação
        Assertions.assertEquals("Tipo de Produto não encontrado na Base de Dados", erroTipoProdutoService.getMessage());
    }

    @Test(expected = Test.None.class)
    public void deveAcharTodosOsClientesComSucesso() {
        //cenário
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(List.of(new TipoProduto()));
        //ação
        List<TipoProdutoDTOView> tipoProdutoEncontrados = service.buscarTodosOsTiposProduto();
        //verificação
        Assertions.assertNotNull(tipoProdutoEncontrados);
    }

    @Test
    public void naoDeveBuscarNenhumCliente() {
        //cenário
        Mockito.when(repository.findAllWhereDataExclusaoIsNull()).thenReturn(Collections.emptyList());
        //ação
        List<TipoProdutoDTOView> tipoProdutoEncontrados = service.buscarTodosOsTiposProduto();
        //verificação
        Assertions.assertTrue(tipoProdutoEncontrados.isEmpty());
    }

    private TipoProduto criaTipoProduto(){
        TipoProdutoDTO dto =TipoProdutoDTO.builder()
                .noTipoProduto("TipoProduto")
                .build();

        TipoProduto tipoProdutoMapeado = mapper.toEntity(dto);
        tipoProdutoMapeado.setId(1L);
        tipoProdutoMapeado.setDataCriacao(LocalDateTime.now());
        tipoProdutoMapeado.setDataAtualizacao(LocalDateTime.now());

        return tipoProdutoMapeado;
    }

}
