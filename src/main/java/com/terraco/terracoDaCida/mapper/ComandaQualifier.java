package com.terraco.terracoDaCida.mapper;

import com.terraco.terracoDaCida.api.dto.ComandaProdutoDTOView;
import com.terraco.terracoDaCida.api.dto.PagamentoDTOView;
import com.terraco.terracoDaCida.model.entity.Cliente;
import com.terraco.terracoDaCida.model.entity.Comanda;
import com.terraco.terracoDaCida.service.ClienteService;
import com.terraco.terracoDaCida.service.ComandaProdutoService;
import com.terraco.terracoDaCida.service.PagamentoService;
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
    @Autowired
    private PagamentoService pagamentoService;

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
        List<PagamentoDTOView> pagamentoDTOViews = pagamentoService.buscarPagamentosDeUmaComanda(idComanda);
        BigDecimal lancamentos = comandaProdutoDTOViews
                        .stream()
                        .map(ComandaProdutoDTOView::getVrTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal pagamentos = pagamentoDTOViews
                .stream()
                .map(PagamentoDTOView::getVrPagamento)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal resultado =  lancamentos.subtract(pagamentos);

        if(resultado.compareTo(BigDecimal.ZERO) > 0){
            return resultado;
        }

        return BigDecimal.ZERO;
    }

    public Cliente dtoViewToEntity(String noCliente){
        return clienteService.buscarClientePorNome(noCliente);
    }
}