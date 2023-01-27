package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.exceptions.ErroPagamentoService;
import com.terraco.terracoDaCida.mapper.PagamentoMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.ComandaProduto;
import com.terraco.terracoDaCida.model.entity.Pagamento;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.PagamentoRepository;
import com.terraco.terracoDaCida.service.PagamentoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        Pagamento pagamentoDeletado = repository.findByIdAndDataExclusaoIsNull(pagamento.getId())
                .orElseThrow(() -> new ErroPagamentoService("Pagamento não encontrado na Base de Dados"));
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
    public List<PagamentoDTOView> buscarPagamentosDeUmaComanda(Long idComanda) {
        List<Pagamento> pagamentoList = repository.findByComandaIdAndDataExclusaoIsNull(idComanda);
        List<PagamentoDTOView> pagamentoDTOViews = new ArrayList<>();
        pagamentoList.forEach(pagamento -> {
            pagamentoDTOViews.add(mapper.toDto(pagamento));
        });
        return pagamentoDTOViews;
    }

    @Override
    public void validarPagamento(Comanda comanda) {
        if(comanda.getSituacaoComanda().equals(SituacaoComandaEnum.PAGA)){
            throw new ErroPagamentoService("Não é possível efetuar pagamento. Comanda já paga anteriormente.");
        }
    }
}
