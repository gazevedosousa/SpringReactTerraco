package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.exceptions.ErroPagamentoService;
import com.terraco.terracoDaCida.exceptions.ErroProdutoService;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.mapper.PagamentoMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Pagamento;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.PagamentoRepository;
import com.terraco.terracoDaCida.service.PagamentoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository repository;
    private final PagamentoMapper mapper = PagamentoMapper.INSTANCE;

    @Override
    @Transactional
    public PagamentoDTOView criar(Pagamento pagamento) {
        validarPagamento(pagamento.getComanda());
        pagamento.setDataCriacao(LocalDateTime.now());
        pagamento.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(pagamento));
    }

    @Override
    @Transactional
    public PagamentoDTOView deletar(Pagamento pagamento) {
        Optional<Pagamento> buscaPagamento = repository.findByIdAndDataExclusaoIsNull(pagamento.getId());

        if(buscaPagamento.isEmpty()){
            throw new ErroPagamentoService("Erro ao buscar pagamento na Base de Dados");
        }

        Pagamento pagamentoDeletado = buscaPagamento.get();
        pagamentoDeletado.setDataExclusao(LocalDateTime.now());
        pagamentoDeletado.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(pagamentoDeletado));
    }

    @Override
    public Pagamento buscarPagamento(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ErroPagamentoService("Pagamento não encontrado na Base de Dados"));
    }

    @Override
    public void validarPagamento(Comanda comanda) {
        if(comanda.getSituacaoComanda().equals(SituacaoComandaEnum.PAGA)){
            throw new ErroPagamentoService("Não é possível efetuar pagamento. Comanda já paga anteriormente.");
        }
    }
}
