package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.exceptions.RegraNegocioException;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.mapper.ComandaProdutoMapper;
import com.terraco.terracoDaCida.mapper.PagamentoMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.ComandaRepository;
import com.terraco.terracoDaCida.service.ComandaProdutoService;
import com.terraco.terracoDaCida.service.ComandaService;
import com.terraco.terracoDaCida.service.PagamentoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ComandaServiceImpl implements ComandaService {

    private final ComandaRepository repository;
    private final PagamentoService pagamentoService;
    private final ComandaProdutoService comandaProdutoService;
    private final ComandaMapper mapper = ComandaMapper.INSTANCE;
    private final PagamentoMapper pagamentoMapper = PagamentoMapper.INSTANCE;
    private final ComandaProdutoMapper comandaProdutoMapper = ComandaProdutoMapper.INSTANCE;

    @Override
    @Transactional
    public ComandaDTOView criar(Comanda comanda) {
        comanda.setDataCriacao(LocalDateTime.now());
        comanda.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comanda));
    }

    @Override
    @Transactional
    public ComandaDTOView fecharComanda(Comanda comanda) {
        Comanda comandaAlterada = repository.findByIdAndDataExclusaoIsNull(comanda.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException(
                        "Erro ao fechar comanda. Comanda não encontrada no Banco de Dados"));

        comandaAlterada.setSituacaoComanda(SituacaoComandaEnum.PAGA);
        comandaAlterada.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comandaAlterada));
    }

    @Override
    @Transactional
    public ComandaDTOView reabrirComanda(Comanda comanda) {
        Comanda comandaAlterada = repository.findByIdAndDataExclusaoIsNull(comanda.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException(
                        "Erro ao reabrir comanda. Comanda não encontrada no Banco de Dados"));

        comandaAlterada.setSituacaoComanda(SituacaoComandaEnum.ABERTA);
        comandaAlterada.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comandaAlterada));
    }

    @Override
    @Transactional
    public ComandaDTOView deletar(Comanda comanda) throws ElementoNaoEncontradoException {
        validaDelecaoComanda(comanda);
        comanda.setDataExclusao(LocalDateTime.now());
        comanda.setDataAtualizacao(LocalDateTime.now());

        return mapper.toDto(repository.save(comanda));
    }

    @Override
    public Comanda buscarComanda(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Comanda não encontrada no Banco de Dados"));
    }


    @Override
    public List<ComandaDTOView> buscarTodasAsComandas() {
        List<Comanda> comandas = repository.findAllWhereDataExclusaoIsNull();
        List<ComandaDTOView> comandaDtoView = new ArrayList<>();

        comandas.forEach(comanda -> {
            comandaDtoView.add(mapper.toDto(comanda));
        });

        return comandaDtoView;
    }

    @Override
    public List<ComandaDTOView> buscarComandasPorCliente(Long idCliente) {
        List<Comanda> comandas = repository.findByIdClienteAndDataExclusaoIsNull(idCliente);
        List<ComandaDTOView> comandaDtoView = new ArrayList<>();

        comandas.forEach(comanda -> {
            comandaDtoView.add(mapper.toDto(comanda));
        });

        return comandaDtoView;
    }

    @Override
    public List<ComandaDTOView> buscarComandasAbertasPorCliente(Long idCliente) {
        List<Comanda> comandas = repository.findByIdClienteAndDataExclusaoIsNull(idCliente);
        List<ComandaDTOView> comandaDtoView = new ArrayList<>();

        comandas.forEach(comanda -> {
            if(comanda.getSituacaoComanda() != SituacaoComandaEnum.PAGA){
                comandaDtoView.add(mapper.toDto(comanda));
            }
        });

        return comandaDtoView;
    }

    @Override
    public void validaDelecaoComanda(Comanda comanda) {
        if(comanda.getSituacaoComanda() == SituacaoComandaEnum.PAGA){
            throw new RegraNegocioException("Erro ao Deletar Comanda. Comanda já paga");
        }
        List<PagamentoDTOView> pagamentoDTOViewList = pagamentoService.buscarPagamentosDeUmaComanda(comanda.getId());
        if(!pagamentoDTOViewList.isEmpty()){
            throw new RegraNegocioException("Erro ao Deletar Comanda. Existe Pagamento cadastrado para a Comanda");
        }
        List<ComandaProdutoDTOView> comandaProdutoDTOViewList = comandaProdutoService.buscarProdutosDeUmaComanda(comanda.getId());
        if(!comandaProdutoDTOViewList.isEmpty()){
            throw new RegraNegocioException("Erro ao Deletar Comanda. Existe Produto cadastrado para a Comanda");
        }
    }
}
