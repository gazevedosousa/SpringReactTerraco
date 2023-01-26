package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ComandaDTOView {

    private Long id;
    private String noCliente;
    private String situacaoComanda;
    private BigDecimal vrComanda;
}
