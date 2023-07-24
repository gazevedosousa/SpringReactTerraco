package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.exceptions.RegraNegocioException;
import com.terraco.terracoDaCida.mapper.ComandaProdutoMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import com.terraco.terracoDaCida.model.entity.Pagamento;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.ComandaProdutoRepository;
import com.terraco.terracoDaCida.service.ComandaProdutoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ComandaProdutoServiceImpl implements ComandaProdutoService {

    private final ComandaProdutoRepository repository;
    private final ComandaProdutoMapper mapper = ComandaProdutoMapper.INSTANCE;


    @Override
    @Transactional
    public ComandaProdutoDTOView criar(ComandaProduto comandaProduto) {
        verificaSituacaoComanda(comandaProduto.getComanda());
        comandaProduto.setDataCriacao(LocalDateTime.now());
        comandaProduto.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comandaProduto));
    }

    @Override
    @Transactional
    public ComandaProdutoDTOView deletar(ComandaProduto comandaProduto) {
        verificaSituacaoComanda(comandaProduto.getComanda());
        ComandaProduto comandaProdutoBanco = repository.findByIdAndDataExclusaoIsNull(comandaProduto.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Lançamento não encontrado no Banco de Dados"));
        comandaProdutoBanco.setDataExclusao(LocalDateTime.now());
        comandaProdutoBanco.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comandaProdutoBanco));
    }

    @Override
    public ComandaProduto buscarComandaProduto(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Lançamento não encontrado no Banco de Dados"));
    }

    @Override
    public List<ComandaProdutoDTOView> buscarProdutosDeUmaComanda(Long idComanda) {
        List<ComandaProduto> comandaProdutos = repository.findByComandaIdAndDataExclusaoIsNull(idComanda);
        List<ComandaProdutoDTOView> comandaProdutoDTOViews = new ArrayList<>();
        comandaProdutos.forEach(comandaProduto -> {
            comandaProdutoDTOViews.add(mapper.toDto(comandaProduto));
        });

        return comandaProdutoDTOViews;
    }

    @Override
    public List<ComandaProdutoDTOView> buscarProdutosEmUmaData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<ComandaProduto> comandaProdutoList = repository.findByDataCriacaoAndDataExclusaoIsNull(LocalDate.parse(data, formatter));
        List<ComandaProdutoDTOView> ComandaProdutoDTOViews = new ArrayList<>();
        comandaProdutoList.forEach(pagamento -> {
            ComandaProdutoDTOViews.add(mapper.toDto(pagamento));
        });

        return ComandaProdutoDTOViews;
    }

    @Override
    public List<ComandaProdutoDTOView> buscarProdutosEmUmMes(String data) {
        String ano = data.substring(0,4);
        String mes = data.substring(data.length() - 2);
        List<ComandaProduto> comandaProdutoList = repository.findByMesCriacaoAndDataExclusaoIsNull(mes,ano);
        List<ComandaProdutoDTOView> ComandaProdutoDTOViews = new ArrayList<>();
        comandaProdutoList.forEach(pagamento -> {
            ComandaProdutoDTOViews.add(mapper.toDto(pagamento));
        });

        return ComandaProdutoDTOViews;
    }

    @Override
    public void verificaSituacaoComanda(Comanda comanda) {
        if(comanda.getSituacaoComanda().equals(SituacaoComandaEnum.PAGA)){
            throw new RegraNegocioException("Não é realizar operação. Comanda já paga.");
        }
    }
}
