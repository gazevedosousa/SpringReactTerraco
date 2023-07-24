package com.terraco.terracoDaCida.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDTO {

    private Long idComanda;
    private String tipoPagamento;
    private BigDecimal vrPagamento;
}
