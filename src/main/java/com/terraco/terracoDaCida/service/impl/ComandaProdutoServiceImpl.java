package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTO;
import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.ProdutoDTOView;
import com.terraco.terracoDaCida.exceptions.ErroComandaProdutoService;
import com.terraco.terracoDaCida.exceptions.ErroComandaService;
import com.terraco.terracoDaCida.exceptions.ErroPagamentoService;
import com.terraco.terracoDaCida.mapper.ClienteMapper;
import com.terraco.terracoDaCida.mapper.ComandaProdutoMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.ComandaProdutoRepository;
import com.terraco.terracoDaCida.service.ComandaProdutoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ComandaProduto comandaProdutoBanco = repository.findByIdAndDataExclusaoIsNull(comandaProduto.getId())
                .orElseThrow(() -> new ErroComandaProdutoService("Lançamento não encontrado"));
        comandaProdutoBanco.setDataExclusao(LocalDateTime.now());
        comandaProdutoBanco.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comandaProdutoBanco));
    }

    @Override
    public ComandaProduto buscarComandaProduto(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ErroComandaProdutoService("Lançamento não encontrado"));
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
    public void verificaSituacaoComanda(Comanda comanda) {
        if(comanda.getSituacaoComanda().equals(SituacaoComandaEnum.PAGA) || comanda.getSituacaoComanda().equals(SituacaoComandaEnum.PENDENTE)){
            throw new ErroComandaProdutoService("Não é possível lançar produto. Comanda " + comanda.getSituacaoComanda() + ".");
        }
    }
}
