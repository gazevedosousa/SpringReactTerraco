package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.TipoProdutoDTOView;
import com.terraco.terracoDaCida.exceptions.ErroTipoProdutoService;
import com.terraco.terracoDaCida.mapper.TipoProdutoMapper;
import com.terraco.terracoDaCida.model.entity.TipoProduto;
import com.terraco.terracoDaCida.model.repository.TipoProdutoRepository;
import com.terraco.terracoDaCida.service.TipoProdutoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TipoProdutoServiceImpl implements TipoProdutoService {

    private final TipoProdutoRepository repository;
    private final TipoProdutoMapper mapper = TipoProdutoMapper.INSTANCE;

    @Override
    @Transactional
    public TipoProdutoDTOView criar(TipoProduto tipoProduto) {
        validarTipoProduto(tipoProduto.getNoTipoProduto());
        tipoProduto.setDataCriacao(LocalDateTime.now());
        tipoProduto.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(tipoProduto));
    }

    @Override
    @Transactional
    public TipoProdutoDTOView deletar(TipoProduto tipoProduto) {
        TipoProduto tipoProdutoDeletado = repository.findByIdAndDataExclusaoIsNull(tipoProduto.getId())
                .orElseThrow(() -> new ErroTipoProdutoService("Tipo de Produto não encontrado na Base de Dados"));
        tipoProdutoDeletado.setDataExclusao(LocalDateTime.now());
        tipoProdutoDeletado.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(tipoProdutoDeletado));
    }

    @Override
    public TipoProduto buscarTipoProduto(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ErroTipoProdutoService("Tipo de Produto não encontrado na Base de Dados"));
    }

    @Override
    public List<TipoProdutoDTOView> buscarTodosOsTiposProduto() {
        List<TipoProduto> tipoProdutos = repository.findAllWhereDataExclusaoIsNull();
        List<TipoProdutoDTOView> tipoProdutoDTOViews = new ArrayList<>();

        tipoProdutos.forEach(tipoProduto -> {
            tipoProdutoDTOViews.add(mapper.toDto(tipoProduto));
        });

        return tipoProdutoDTOViews;
    }

    @Override
    public void validarTipoProduto(String noTipoProduto) {
        boolean existe = repository.existsByNoTipoProdutoAndDataExclusaoIsNull(noTipoProduto);

        if(existe){
            throw new ErroTipoProdutoService("Tipo de Produto já existe no Banco de Dados");
        }
    }
}
