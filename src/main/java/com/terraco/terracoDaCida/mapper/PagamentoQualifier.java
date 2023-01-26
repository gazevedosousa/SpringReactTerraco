package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.service.ComandaService;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper
public class PagamentoQualifier {
    @Autowired
    private ComandaService comandaService;

    static protected PagamentoQualifier FIRST_INSTANCE;

    public PagamentoQualifier() {
        if(FIRST_INSTANCE == null) {
            FIRST_INSTANCE = this;
        }
    }

    public static PagamentoQualifier getFirstInstance(){
        return FIRST_INSTANCE;
    }

    public Comanda dtoToEntity(Long idComanda){
        return comandaService.buscarComanda(idComanda);
    }
    public Long entityToDTO(Comanda comanda){
        return comanda.getId();
    }

    public String entityToDTOCliente(Comanda comanda){
        return String.valueOf(comanda.getCliente().getNoCliente());
    }


}