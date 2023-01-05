package com.terraco.terracoDaCida.api.dto;

import com.terraco.terracoDaCida.model.entity.TipoLogin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class LoginDTO {

    private String noUsuario;
    private String coSenha;
    private Long tipoLogin;
    private LocalDate dhCriacao;
    private LocalDate dhAtualizacao;
    private LocalDate dhExclusao;
}
