package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginDTOView {

    private Long id;
    private String noUsuario;
    private String perfil;
}
