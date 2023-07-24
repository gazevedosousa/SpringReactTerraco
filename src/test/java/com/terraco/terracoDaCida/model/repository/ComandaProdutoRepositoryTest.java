package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ProdutoDTO;
import com.terraco.terracoDaCida.api.dto.TipoProdutoDTO;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
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

public class ComandaProdutoRepositoryTest {
    @Autowired
    ComandaProdutoRepository repository;
    @Autowired
    TestEntityManager entityManager;
    @Spy
    private ComandaProdutoMapper mapper = Mappers.getMapper(ComandaProdutoMapper.class);
    @Spy
    private ComandaMapper comandaMapper = Mappers.getMapper(ComandaMapper.class);
    @Spy
    private ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);
    @Spy
    private ProdutoMapper produtoMapper = Mappers.getMapper(ProdutoMapper.class);
    @Spy
    private TipoProdutoMapper tipoProdutoMapper = Mappers.getMapper(TipoProdutoMapper.class);

    @Test
    public void deveAcharUmaListaDeComandaProdutoNoBanco(){
        //cenário
        List<ComandaProduto> comandaProdutos = new ArrayList<>();

        Cliente clientePersistido = entityManager.persist(criaCliente());
        Comanda comandaPersistida = entityManager.persist(criaComanda(clientePersistido.getId()));

        TipoProduto tipoProdutoPersistido = entityManager.persist(criaTipoProduto());
        Produto produtoPersistido = entityManager.persist(criaProduto(tipoProdutoPersistido.getId()));

        ComandaProduto comandaProduto1 = entityManager.persist(criaComandaProduto(comandaPersistida.getId(), produtoPersistido.getId()));
        comandaProdutos.add(comandaProduto1);
        ComandaProduto comandaProduto2 = entityManager.persist(criaComandaProduto(comandaPersistida.getId(), produtoPersistido.getId()));
        comandaProdutos.add(comandaProduto2);
        ComandaProduto comandaProduto3 = entityManager.persist(criaComandaProduto(comandaPersistida.getId(), produtoPersistido.getId()));
        comandaProdutos.add(comandaProduto3);
        //ação
        List<ComandaProduto> comandaProdutoList = repository.findByComandaIdAndDataExclusaoIsNull(comandaProduto1.getId());
        //verificação
        Assertions.assertEquals(comandaProdutos ,comandaProdutoList);
    }

    @Test
    public void deveRetornarUmaComandaProdutoComBaseNoIdAchadoNoBanco(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        Comanda comandaPersistida = entityManager.persist(criaComanda(clientePersistido.getId()));
        TipoProduto tipoProdutoPersistido = entityManager.persist(criaTipoProduto());
        Produto produtoPersistido = entityManager.persist(criaProduto(tipoProdutoPersistido.getId()));
        ComandaProduto comandaProdutoPersistida = entityManager.persist(criaComandaProduto(comandaPersistida.getId(), produtoPersistido.getId()));
        //ação
        ComandaProduto comandaProduto = repository.findByIdAndDataExclusaoIsNull(comandaProdutoPersistida.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Comanda Produto não Encontrada") );
        //verificação
        Assertions.assertNotNull(comandaProduto);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveRetornarErroQuandoNaoAcharUmaComandaProdutoComBaseNoId(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        Comanda comandaPersistida = entityManager.persist(criaComanda(clientePersistido.getId()));
        TipoProduto tipoProdutoPersistido = entityManager.persist(criaTipoProduto());
        Produto produtoPersistido = entityManager.persist(criaProduto(tipoProdutoPersistido.getId()));
        ComandaProduto comandaProdutoPersistida = entityManager.persist(criaComandaProduto(comandaPersistida.getId(), produtoPersistido.getId()));
        //ação
        ComandaProduto comandaProduto = repository.findByIdAndDataExclusaoIsNull(2L)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Comanda Produto não encontrada") );
        //verificação
        Assertions.assertNotNull(comandaProduto);
    }

    private ComandaProduto criaComandaProduto(Long idComanda, Long idProduto){
        ComandaProdutoDTO dto =ComandaProdutoDTO.builder()
                .idComanda(idComanda)
                .idProduto(idProduto)
                .build();

        ComandaProduto comandaProduto = mapper.toEntity(dto);
        comandaProduto.setDataCriacao(LocalDateTime.now());
        comandaProduto.setDataAtualizacao(LocalDateTime.now());

        return comandaProduto;
    }

    private Cliente criaCliente(){
        ClienteDTO dto =ClienteDTO.builder()
                .noCliente("Cliente")
                .celCliente("31993842939")
                .emailCliente("foo@foo.com.br")
                .build();

        Cliente clienteMapeado = clienteMapper.toEntity(dto);
        clienteMapeado.setDataCriacao(LocalDateTime.now());
        clienteMapeado.setDataAtualizacao(LocalDateTime.now());

        return clienteMapeado;
    }

    private Comanda criaComanda(Long idCliente){
        ComandaDTO dto =ComandaDTO.builder()
                .idCliente(idCliente)
                .situacaoComanda("ABERTA")
                .build();

        Comanda comanda = comandaMapper.toEntity(dto);
        comanda.setDataCriacao(LocalDateTime.now());
        comanda.setDataAtualizacao(LocalDateTime.now());

        return comanda;
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
                .vrProduto(BigDecimal.ONE)
                .build();

        Produto produto = produtoMapper.toEntity(dto);
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataAtualizacao(LocalDateTime.now());

        return produto;
    }

}

