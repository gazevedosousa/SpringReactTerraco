package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.service.ClienteService;
import com.terraco.terracoDaCida.service.ComandaProdutoService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Mapper
public class ComandaQualifier {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ComandaProdutoService comandaProdutoService;

    static protected ComandaQualifier FIRST_INSTANCE;

    public ComandaQualifier() {
        if(FIRST_INSTANCE == null) {
            FIRST_INSTANCE = this;
        }
    }

    public static ComandaQualifier getFirstInstance(){
        return FIRST_INSTANCE;
    }

    public Cliente dtoToEntity(Long id){
        return clienteService.buscarCliente(id);
    }
    public String entityToDTO(Cliente cliente){
        return cliente.getNoCliente();
    }

    public BigDecimal entityToDTOValorComanda(Long idComanda){
        List<ComandaProdutoDTOView> comandaProdutoDTOViews = comandaProdutoService.buscarProdutosDeUmaComanda(idComanda);

        return comandaProdutoDTOViews
                .stream()
                .map(ComandaProdutoDTOView::getVrProduto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}