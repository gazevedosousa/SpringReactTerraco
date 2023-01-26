package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteDTOView {

    private Long id;
    private String noCliente;
    private String celCliente;
    private String emailCliente;
}
