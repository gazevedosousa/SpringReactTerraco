package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.api.dto.ComandaDTO;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.entity.Comanda;
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

public class ComandaRepositoryTest {
    @Autowired
    ComandaRepository repository;
    @Autowired
    TestEntityManager entityManager;
    @Spy
    private ComandaMapper mapper = Mappers.getMapper(ComandaMapper.class);
    @Spy
    private ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);

    @Test
    public void deveAcharTodasAsComandas(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        Comanda comanda1 = entityManager.persist(criaComanda(clientePersistido.getId()));
        Comanda comanda2 = entityManager.persist(criaComanda(clientePersistido.getId()));
        Comanda comanda3 = criaComanda(clientePersistido.getId());
        comanda3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(comanda3);
        List<Comanda> comandas = new ArrayList<>();
        comandas.add(comanda1);
        comandas.add(comanda2);
        //ação
        List<Comanda> comandasList = repository.findAllWhereDataExclusaoIsNull();
        //verificação
        Assertions.assertEquals(comandas, comandasList);
    }

    @Test
    public void deveAcharListaDeComandasDeCliente(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        Comanda comanda1 = entityManager.persist(criaComanda(clientePersistido.getId()));
        Comanda comanda2 = entityManager.persist(criaComanda(clientePersistido.getId()));
        Comanda comanda3 = criaComanda(clientePersistido.getId());
        comanda3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(comanda3);
        List<Comanda> comandas = new ArrayList<>();
        comandas.add(comanda1);
        comandas.add(comanda2);
        //ação
        List<Comanda> comandasList = repository.findByIdClienteAndDataExclusaoIsNull(clientePersistido.getId());
        //verificação
        Assertions.assertEquals(comandas, comandasList);
    }

    @Test
    public void deveAcharUmaComanda(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        Comanda comandaPersistida = entityManager.persist(criaComanda(clientePersistido.getId()));
        //ação
        Comanda comandaDoBanco = repository.findByIdAndDataExclusaoIsNull(comandaPersistida.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Comanda não Encontrada") );
        //verificação
        Assertions.assertNotNull(comandaDoBanco);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveRetornarErroAoNaoAcharComandaComBaseNoId(){
        //verificação
        Comanda comanda = repository.findByIdAndDataExclusaoIsNull(1l)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Comanda não Encontrada") );
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

        Comanda comanda = mapper.toEntity(dto);
        comanda.setDataCriacao(LocalDateTime.now());
        comanda.setDataAtualizacao(LocalDateTime.now());

        return comanda;
    }
}