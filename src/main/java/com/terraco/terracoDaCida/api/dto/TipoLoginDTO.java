package com.terraco.terracoDaCida.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class TipoLoginDTO {

    private String noTipoLogin;
    private LocalDate dhCriacao;
    private LocalDate dhAtualizacao;
    private LocalDate dhExclusao;
}
