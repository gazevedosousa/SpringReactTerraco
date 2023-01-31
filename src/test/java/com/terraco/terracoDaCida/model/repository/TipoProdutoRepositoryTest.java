package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.api.dto.TipoProdutoDTO;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.TipoProdutoMapper;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
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

public class TipoProdutoRepositoryTest {
    @Autowired
    TipoProdutoRepository repository;
    @Autowired
    TestEntityManager entityManager;
    @Spy
    private TipoProdutoMapper mapper = Mappers.getMapper(TipoProdutoMapper.class);

    @Test
    public void deveAcharTodosOsTiposDeProdutosComDataDeExclusaoNula(){
        //cenário
        TipoProduto tipoProduto1 = entityManager.persist(criaTipoProduto());
        TipoProduto tipoProduto2 = criaTipoProduto();
        tipoProduto2.setNoTipoProduto("TipoProduto2");
        entityManager.persist(tipoProduto2);
        TipoProduto tipoProduto3 = criaTipoProduto();
        tipoProduto3.setNoTipoProduto("TipoProduto3");
        tipoProduto3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(tipoProduto3);
        List<TipoProduto> tipoProdutosNaoExcluidos = new ArrayList<>();
        tipoProdutosNaoExcluidos.add(tipoProduto1);
        tipoProdutosNaoExcluidos.add(tipoProduto2);
        //ação
        List<TipoProduto> tipoProdutoList = repository.findAllWhereDataExclusaoIsNull();
        //verificação
        Assertions.assertEquals(tipoProdutosNaoExcluidos, tipoProdutoList);
    }

    @Test
    public void deveAcharTodosOsTiposDeProdutos(){
        //cenário
        TipoProduto tipoProduto1 = entityManager.persist(criaTipoProduto());
        TipoProduto tipoProduto2 = criaTipoProduto();
        tipoProduto2.setNoTipoProduto("TipoProduto2");
        entityManager.persist(tipoProduto2);
        TipoProduto tipoProduto3 = criaTipoProduto();
        tipoProduto3.setNoTipoProduto("TipoProduto3");
        tipoProduto3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(tipoProduto3);
        List<TipoProduto> tipoProdutosNaoExcluidos = new ArrayList<>();
        tipoProdutosNaoExcluidos.add(tipoProduto1);
        tipoProdutosNaoExcluidos.add(tipoProduto2);
        tipoProdutosNaoExcluidos.add(tipoProduto3);
        //ação
        List<TipoProduto> tipoProdutoList = repository.findAll();
        //verificação
        Assertions.assertEquals(tipoProdutosNaoExcluidos, tipoProdutoList);
    }

    @Test
    public void deveRetornarUmTipoDeProduto(){
        //cenário
        TipoProduto tipoProduto = criaTipoProduto();
        tipoProduto.setDataExclusao(LocalDateTime.now());
        TipoProduto tipoProdutoPersistido = entityManager.persist(tipoProduto);
        //ação
        TipoProduto tipoProdutoDoBanco = repository.findById(tipoProdutoPersistido.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Tipo de Produto não Encontrado") );
        //verificação
        Assertions.assertNotNull(tipoProdutoDoBanco);
    }

    @Test
    public void deveVerificarAExistenciaDeUmTipoDeProdutoComBaseNoNomeDoTipoProduto(){
        //cenário
        entityManager.persist(criaTipoProduto());
        //ação
        boolean existe = repository.existsByNoTipoProdutoAndDataExclusaoIsNull("TipoProduto");
        //verificação
        Assertions.assertTrue(existe);
    }

    @Test
    public void deveRetornarFalseQuandoNaoExisteNenhumTipoDeProdutoNoBanco(){
        //cenário

        //ação
        boolean existe = repository.existsByNoTipoProdutoAndDataExclusaoIsNull("TipoProduto");
        //verificação
        Assertions.assertFalse(existe);
    }

    @Test
    public void deveRetornarUmTipoDeProdutoComBaseNoIdAchadoNoBanco(){
        //cenário
        TipoProduto tipoProduto = entityManager.persist(criaTipoProduto());
        //ação
        TipoProduto tipoProdutoDoBanco = repository.findByIdAndDataExclusaoIsNull(tipoProduto.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Tipo de Produto não Encontrado") );
        //verificação
        Assertions.assertNotNull(tipoProdutoDoBanco);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveRetornarErroAoNaoAcharTipoDeProdutoComBaseNoId(){
        //verificação
        TipoProduto tipoProdutoDoBanco = repository.findByIdAndDataExclusaoIsNull(1L)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Tipo de Produto não Encontrado") );
    }


    private TipoProduto criaTipoProduto(){
        TipoProdutoDTO dto =TipoProdutoDTO.builder()
                .noTipoProduto("TipoProduto")
                .build();

        TipoProduto tipoProduto = mapper.toEntity(dto);
        tipoProduto.setDataCriacao(LocalDateTime.now());
        tipoProduto.setDataAtualizacao(LocalDateTime.now());

        return tipoProduto;
    }
}