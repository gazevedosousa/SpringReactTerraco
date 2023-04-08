package com.terraco.terracoDaCida.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandaDTOView {

    private Long id;
    private String noCliente;
    private String situacaoComanda;
    private LocalDate dtComanda;
    private BigDecimal vrComanda;
}
