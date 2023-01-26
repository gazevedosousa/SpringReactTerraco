package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ComandaProdutoDTO {

    private Long idComanda;
    private Long idProduto;
}
