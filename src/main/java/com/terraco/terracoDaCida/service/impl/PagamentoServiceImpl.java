package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.exceptions.ElementoNaoEncontradoException;
import com.terraco.terracoDaCida.exceptions.RegraNegocioException;
import com.terraco.terracoDaCida.mapper.PagamentoMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Pagamento;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.PagamentoRepository;
import com.terraco.terracoDaCida.service.ComandaService;
import com.terraco.terracoDaCida.service.PagamentoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository repository;

    private final PagamentoMapper mapper = PagamentoMapper.INSTANCE;

    @Override
    @Transactional
    public PagamentoDTOView pagarParcial(Pagamento pagamento) {
        validarPagamento(pagamento.getComanda());
        pagamento.setDataCriacao(LocalDateTime.now());
        pagamento.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(pagamento));
    }

    @Override
    public PagamentoDTOView pagarTotal(Pagamento pagamento) {
        validarPagamento(pagamento.getComanda());
        pagamento.setDataCriacao(LocalDateTime.now());
        pagamento.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(pagamento));
    }

    @Override
    public PagamentoDTOView estornarPagamento(Pagamento pagamento) {
        Pagamento pagamentoEstornado = repository.findByIdAndDataExclusaoIsNull(pagamento.getId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Pagamento não encontrado no Banco de Dados"));
        pagamentoEstornado.setDataExclusao(LocalDateTime.now());
        pagamentoEstornado.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(pagamentoEstornado));
    }


    @Override
    public Pagamento buscarPagamento(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Pagamento não encontrado no Banco de Dados"));
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
            throw new RegraNegocioException("Não é possível realizar operação. Comanda já paga anteriormente.");
        }
    }
}
