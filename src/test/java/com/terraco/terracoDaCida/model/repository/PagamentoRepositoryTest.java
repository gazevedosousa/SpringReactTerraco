package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.api.dto.PagamentoDTO;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.mapper.PagamentoMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Pagamento;
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

public class PagamentoRepositoryTest {
    @Autowired
    PagamentoRepository repository;
    @Autowired
    TestEntityManager entityManager;
    @Spy
    private PagamentoMapper mapper = Mappers.getMapper(PagamentoMapper.class);
    @Spy
    private ComandaMapper comandaMapper = Mappers.getMapper(ComandaMapper.class);
    @Spy
    private ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);

    @Test
    public void deveAcharTodosOsPagamentosDeUmaComandaBuscandoPorIdComanda(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        Comanda comandaPersistida = entityManager.persist(criaComanda(clientePersistido.getId()));
        Pagamento pagamento1 = entityManager.persist(criaPagamento(comandaPersistida.getId()));
        Pagamento pagamento2 = entityManager.persist(criaPagamento(comandaPersistida.getId()));
        Pagamento pagamento3 = criaPagamento(comandaPersistida.getId());
        pagamento3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(pagamento3);

        List<Pagamento> pagamentosNaoExcluidos = new ArrayList<>();
        pagamentosNaoExcluidos.add(pagamento1);
        pagamentosNaoExcluidos.add(pagamento2);
        //ação
        List<Pagamento> pagamentoList = repository.findByComandaIdAndDataExclusaoIsNull(comandaPersistida.getId());
        //verificação
        Assertions.assertEquals(pagamentosNaoExcluidos, pagamentoList);
    }

    @Test
    public void deveRetornarUmPagamentoComBaseNoIdAchadoNoBanco(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        Comanda comandaPersistida = entityManager.persist(criaComanda(clientePersistido.getId()));
        Pagamento pagamentoPersistido = entityManager.persist(criaPagamento(comandaPersistida.getId()));
        //ação
        Pagamento pagamentoDoBanco = repository.findByIdAndDataExclusaoIsNull(pagamentoPersistido.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Pagamento não Encontrado") );
        //verificação
        Assertions.assertNotNull(pagamentoDoBanco);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveRetornarErroAoNaoAcharPagamentoComBaseNoId(){
        //verificação
        Pagamento pagamentoDoBanco = repository.findByIdAndDataExclusaoIsNull(1L)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Pagamento não Encontrado") );
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

    private Pagamento criaPagamento(Long idComanda){
        PagamentoDTO dto =PagamentoDTO.builder()
                .idComanda(idComanda)
                .tipoPagamento("DEBITO")
                .vrPagamento(BigDecimal.TEN)
                .build();

        Pagamento pagamento = mapper.toEntity(dto);
        pagamento.setDataCriacao(LocalDateTime.now());
        pagamento.setDataAtualizacao(LocalDateTime.now());

        return pagamento;
    }
}