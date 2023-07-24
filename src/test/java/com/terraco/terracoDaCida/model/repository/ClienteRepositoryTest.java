package com.terraco.terracoDaCida.model.repository;

import com.terraco.terracoDaCida.api.dto.ClienteDTO;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.model.entity.Cliente;
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

public class ClienteRepositoryTest {
    @Autowired
    ClienteRepository repository;
    @Autowired
    TestEntityManager entityManager;
    @Spy
    private ClienteMapper mapper = Mappers.getMapper(ClienteMapper.class);
    @Test
    public void deveVerificarAExistenciaDeUmClienteComBaseNoNomeDoUsuario(){
        //cenário
        entityManager.persist(criaCliente());
        //ação
        boolean existe = repository.existsByNoClienteAndDataExclusaoIsNull("Cliente");
        //verificação
        Assertions.assertTrue(existe);
    }

    @Test
    public void deveRetornarFalseQuandoNaoExisteNenhumClienteNoBanco(){
        //cenário

        //ação
        boolean existe = repository.existsByNoClienteAndDataExclusaoIsNull("Cliente");
        //verificação
        Assertions.assertFalse(existe);
    }

    @Test
    public void deveAcharTodosOsClientesQueNaoEstaoExcluidos(){
        //cenário
        Cliente cliente1 = entityManager.persist(criaCliente());
        Cliente cliente2 = criaCliente();
        cliente2.setNoCliente("cliente2");
        entityManager.persist(cliente2);
        Cliente cliente3 = criaCliente();
        cliente3.setNoCliente("cliente3");
        cliente3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(cliente3);

        List<Cliente> clienteNaoExcluido = new ArrayList<>();
        clienteNaoExcluido.add(cliente1);
        clienteNaoExcluido.add(cliente2);
        //ação
        List<Cliente> clienteList = repository.findAllWhereDataExclusaoIsNull();
        //verificação
        Assertions.assertEquals(clienteNaoExcluido, clienteList);
    }

    @Test
    public void deveAcharTodosOsClientes(){
        //cenário
        Cliente cliente1 = entityManager.persist(criaCliente());
        Cliente cliente2 = criaCliente();
        cliente2.setNoCliente("cliente2");
        entityManager.persist(cliente2);
        Cliente cliente3 = criaCliente();
        cliente3.setNoCliente("cliente3");
        cliente3.setDataExclusao(LocalDateTime.now());
        entityManager.persist(cliente3);

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);
        //ação
        List<Cliente> clienteList = repository.findAll();
        //verificação
        Assertions.assertEquals(clientes, clienteList);
    }

    @Test
    public void deveRetornarUmUsuarioNaoExcluidoComBaseNoIdAchadoNoBanco(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        //ação
        Cliente clienteDoBanco = repository.findByIdAndDataExclusaoIsNull(clientePersistido.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Cliente não Encontrado") );
        //verificação
        Assertions.assertNotNull(clienteDoBanco);
    }

    @Test
    public void deveRetornarUmUsuarioComBaseNoIdAchadoNoBanco(){
        //cenário
        Cliente clientePersistido = entityManager.persist(criaCliente());
        //ação
        Cliente clienteDoBanco = repository.findById(clientePersistido.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Cliente não Encontrado") );
        //verificação
        Assertions.assertNotNull(clienteDoBanco);
    }

    @Test(expected = ElementoNaoEncontradoException.class)
    public void deveRetornarErroAoNaoAcharUsuarioComBaseNoId(){
        //verificação
        Cliente clienteDoBanco = repository.findByIdAndDataExclusaoIsNull(1l)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Cliente não Encontrado") );
    }

    private Cliente criaCliente(){
        ClienteDTO dto =ClienteDTO.builder()
                .noCliente("Cliente")
                .celCliente("31993842939")
                .emailCliente("foo@foo.com.br")
                .build();

        Cliente clienteMapeado = mapper.toEntity(dto);
        clienteMapeado.setDataCriacao(LocalDateTime.now());
        clienteMapeado.setDataAtualizacao(LocalDateTime.now());

        return clienteMapeado;
    }
}