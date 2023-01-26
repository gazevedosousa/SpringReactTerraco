package com.terraco.terracoDaCida.service.impl;

import com.terraco.terracoDaCida.api.dto.ComandaDTOView;
import com.terraco.terracoDaCida.api.dto.LoginDTOView;
import com.terraco.terracoDaCida.exceptions.ErroComandaService;
import com.terraco.terracoDaCida.mapper.ComandaMapper;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.model.entity.Login;
import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import com.terraco.terracoDaCida.model.repository.ComandaRepository;
import com.terraco.terracoDaCida.service.ComandaService;
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
    private final ComandaMapper mapper = ComandaMapper.INSTANCE;

    @Override
    @Transactional
    public ComandaDTOView criar(Comanda comanda) {
        comanda.setDataCriacao(LocalDateTime.now());
        comanda.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comanda));
    }

    @Override
    @Transactional
    public ComandaDTOView deletar(Comanda comanda) throws ErroComandaService {
        Comanda comandaDeletada = repository.findByIdAndDataExclusaoIsNull(comanda.getId())
                .orElseThrow(() -> new ErroComandaService("Comanda não encontrada na Base de Dados"));
        comandaDeletada.setDataExclusao(LocalDateTime.now());
        comandaDeletada.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comandaDeletada));
    }

    @Override
    @Transactional
    public ComandaDTOView alterarSituacao(Comanda comanda,  String novaSituacaoComanda) {
        Comanda comandaAlterada = repository.findByIdAndDataExclusaoIsNull(comanda.getId())
                .orElseThrow(() -> new ErroComandaService(
                        "Erro ao alterar comanda. Comanda não encontrada na Base de Dados"));

        comandaAlterada.setSituacaoComanda(SituacaoComandaEnum.valueOf(novaSituacaoComanda));
        comandaAlterada.setDataAtualizacao(LocalDateTime.now());
        return mapper.toDto(repository.save(comandaAlterada));
    }

    @Override
    public Comanda buscarComanda(Long id) {
        return repository.findByIdAndDataExclusaoIsNull(id)
                .orElseThrow(() -> new ErroComandaService("Comanda não encontrada na Base de Dados"));
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
}
