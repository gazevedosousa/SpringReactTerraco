package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.api.dto.ProdutoDTO;
import com.terraco.terracoDaCida.api.dto.TipoProdutoDTO;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.ProdutoMapper;
import com.terraco.terracoDaCida.mapper.TipoProdutoMapper;
import com.terraco.terracoDaCida.model.entity.Produto;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")

public class ProdutoRepositoryTest {
    @Autowired
    ProdutoRepository repository;
    @Autowired
    TestEntityManager entityManager;
    @Spy
    private ProdutoMapper mapper = Mappers.getMapper(ProdutoMapper.class);
    @Spy
    private TipoProdutoMapper tipoProdutoMapper = Mappers.getMapper(TipoProdutoMapper.class);

    @Test
    public void deveAcharTodosOsProdutosComDataDeExclusaoNula(){
        //cenário
        TipoProduto tipoProduto = entityManager.persist(criaTipoProduto());
        Produto produto1 = entityManager.persist(criaProduto(tipoProduto.getId()));
        Produto produto2 = criaProduto(tipoProduto.getId());
        produto2.setNoProduto("Produto2");
        entityManager.persist(produto2);
        Produto produto3 = criaProduto(tipoProduto.getId());
        produto3.setNoProduto("Produto3");
        produto3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(produto3);
        List<Produto> produtosNaoExcluidos = new ArrayList<>();
        produtosNaoExcluidos.add(produto1);
        produtosNaoExcluidos.add(produto2);
        //ação
        List<Produto> produtoList = repository.findAllWhereDataExclusaoIsNull();
        //verificação
        Assertions.assertEquals(produtosNaoExcluidos, produtoList);
    }

    @Test
    public void deveAcharTodosOsProdutosDeUmTipoEspecifico(){
        //cenário
        TipoProduto tipoProduto = entityManager.persist(criaTipoProduto());
        TipoProduto tipoProduto2 = criaTipoProduto();
        tipoProduto2.setNoTipoProduto("TipoProduto2");
        entityManager.persist(tipoProduto2);
        Produto produto1 = entityManager.persist(criaProduto(tipoProduto.getId()));
        Produto produto2 = criaProduto(tipoProduto.getId());
        produto2.setNoProduto("Produto2");
        entityManager.persist(produto2);
        Produto produto3 = criaProduto(tipoProduto.getId());
        produto3.setNoProduto("Produto3");
        produto3.setTipoProduto(tipoProduto2);
        entityManager.persist(produto3);
        List<Produto> produtosTipo1 = new ArrayList<>();
        produtosTipo1.add(produto1);
        produtosTipo1.add(produto2);
        //ação
        List<Produto> produtoList = repository.findAllWhereTipoProdutoAndDataExclusaoIsNull(tipoProduto.getId());
        //verificação
        Assertions.assertEquals(produtosTipo1, produtoList);
    }

    @Test
    public void deveVerificarAExistenciaProdutoComBaseNoNomeDoProduto(){
        //cenário
        TipoProduto tipoProduto = entityManager.persist(criaTipoProduto());
        Produto produto = entityManager.persist(criaProduto(tipoProduto.getId()));
        //ação
        boolean existe = repository.existsByNoProdutoAndDataExclusaoIsNull("Produto");
        //verificação
        Assertions.assertTrue(existe);
    }

    @Test
    public void deveRetornarFalseQuandoNaoExisteNenhumProdutoNoBanco(){
        //cenário

        //ação
        boolean existe = repository.existsByNoProdutoAndDataExclusaoIsNull("Produto");
        //verificação
        Assertions.assertFalse(existe);
    }

    @Test
    public void deveRetornarUmProdutoComBaseNoIdAchadoNoBanco(){
        //cenário
        TipoProduto tipoProduto = entityManager.persist(criaTipoProduto());
        Produto produto = entityManager.persist(criaProduto(tipoProduto.getId()));
        //ação
        Produto produtoDoBanco = repository.findByIdAndDataExclusaoIsNull(produto.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Produto não Encontrado") );
        //verificação
        Assertions.assertNotNull(produtoDoBanco);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveRetornarErroAoNaoAcharTipoDeProdutoComBaseNoId(){
        //verificação
        Produto produtoDoBanco = repository.findByIdAndDataExclusaoIsNull(1L)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Produto não Encontrado") );
    }


    private TipoProduto criaTipoProduto(){
        TipoProdutoDTO dto =TipoProdutoDTO.builder()
                .noTipoProduto("TipoProduto")
                .build();

        TipoProduto tipoProduto = tipoProdutoMapper.toEntity(dto);
        tipoProduto.setDataCriacao(LocalDateTime.now());
        tipoProduto.setDataAtualizacao(LocalDateTime.now());

        return tipoProduto;
    }

    private Produto criaProduto(Long idTipoProduto){
        ProdutoDTO dto =ProdutoDTO.builder()
                .noProduto("Produto")
                .idTipoProduto(idTipoProduto)
                .vrProduto(BigDecimal.TEN)
                .build();

        Produto produto = mapper.toEntity(dto);
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataAtualizacao(LocalDateTime.now());

        return produto;
    }
}