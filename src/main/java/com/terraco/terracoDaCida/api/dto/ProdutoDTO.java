package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProdutoDTO {
    private String noProduto;
    private BigDecimal vrProduto;
    private Long idTipoProduto;
}
