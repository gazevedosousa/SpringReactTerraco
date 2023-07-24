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
public class ComandaProdutoDTOView {
    private Long id;
    private Long idComanda;
    private String noCliente;
    private Long idProduto;
    private String noProduto;
    private LocalDate dtLancamento;
    private Long quantidade;
    private BigDecimal vrUnitario;
    private BigDecimal vrTotal;
}
