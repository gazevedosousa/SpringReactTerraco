package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.ProdutoDTOView;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.exceptions.RegraNegocioException;
import com.terraco.terracoDaCida.mapper.ProdutoMapper;
import com.terraco.terracoDaCida.model.entity.Produto;
import com.terraco.terracoDaCida.model.repository.ProdutoRepository;
import com.terraco.terracoDaCida.service.ProdutoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;

    private final ProdutoMapper mapper = ProdutoMapper.INSTANCE;

    @Override
    @Transactional
    public ProdutoDTOView criar(Produto produto) {
        validarProduto(produto.getNoProduto());
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(produto));
    }

    @Override
    @Transactional
    public ProdutoDTOView atualizar(Produto produto, BigDecimal novoVrProduto) {
        Produto produtoAtualizado = repository.findByIdAndDataExclusaoIsNull(produto.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Produto não encontrado no Banco de Dados"));
        produtoAtualizado.setVrProduto(novoVrProduto);
        produtoAtualizado.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(produtoAtualizado));
    }

    @Override
    @Transactional
    public ProdutoDTOView deletar(Produto produto) {
        Produto produtoDeletado = repository.findByIdAndDataExclusaoIsNull(produto.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Produto não encontrado no Banco de Dados"));
        produtoDeletado.setDataExclusao(LocalDateTime.now());
        produtoDeletado.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(produtoDeletado));
    }

    @Override
    public Produto buscarProdutoNaoExcluido(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Produto não encontrado no Banco de Dados"));
    }

    @Override
    public List<ProdutoDTOView> buscarTodosOsProdutosNaoExcluidos() {
        List<Produto> produtos = repository.findAllWhereDataExclusaoIsNull();
        List<ProdutoDTOView> produtoDTOViews = new ArrayList<>();

        produtos.forEach(produto -> {
            produtoDTOViews.add(mapper.toDto(produto));
        });

        if(produtoDTOViews.isEmpty()){
            throw new ElementoNaoEncontradoException("Nenhum Produto encontrado no Banco de Dados");
        }
        return produtoDTOViews;
    }

    @Override
    public Produto buscarProduto(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Produto não encontrado no Banco de Dados"));
    }

    @Override
    public List<ProdutoDTOView> buscarTodosOsProdutos() {
        List<Produto> produtos = repository.findAll();
        List<ProdutoDTOView> produtoDTOViews = new ArrayList<>();

        produtos.forEach(produto -> {
            produtoDTOViews.add(mapper.toDto(produto));
        });

        if(produtoDTOViews.isEmpty()){
            throw new ElementoNaoEncontradoException("Nenhum Produto encontrado no Banco de Dados");
        }
        return produtoDTOViews;
    }

    @Override
    public List<ProdutoDTOView> buscarProdutosPorTipo(Long idTipoProduto) {
        List<Produto> produtos = repository.findAllWhereTipoProdutoAndDataExclusaoIsNull(idTipoProduto);
        List<ProdutoDTOView> produtoDTOViews = new ArrayList<>();

        produtos.forEach(produto -> {
            produtoDTOViews.add(mapper.toDto(produto));
        });

        if(produtoDTOViews.isEmpty()){
            throw new ElementoNaoEncontradoException("Nenhum Produto encontrado no Banco de Dados");
        }
        return produtoDTOViews;
    }

    @Override
    public void validarProduto(String noProduto) {
        boolean existe = repository.existsByNoProdutoAndDataExclusaoIsNull(noProduto);

        if(existe){
            throw new RegraNegocioException("Produto já existe no Banco de Dados");
        }
    }

}
