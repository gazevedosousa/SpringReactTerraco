package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ComandaProdutoDTOView {
    private Long id;
    private Long idComanda;
    private String noCliente;
    private Long idProduto;
    private String noProduto;
    private BigDecimal vrProduto;
}
