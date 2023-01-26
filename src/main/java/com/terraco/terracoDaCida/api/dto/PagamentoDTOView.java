package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PagamentoDTOView {

    private Long id;
    private Long idComanda;
    private String noCliente;
    private String tipoPagamento;
    private BigDecimal vrPagamento;
}
