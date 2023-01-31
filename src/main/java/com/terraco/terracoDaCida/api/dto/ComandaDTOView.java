package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ComandaDTOView {

    private Long id;
    private String noCliente;
    private String situacaoComanda;
    private LocalDate dtComanda;
    private BigDecimal vrComanda;
}
