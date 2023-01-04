package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name="login")
@Data
@RequiredArgsConstructor
@Builder
public class Login {
    @Id
    @Column(name = "co_login")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coLogin;

    @Column(name = "no_usuario", unique = true)
    private String noUsuario;

    @Column(name = "co_senha")
    private String coSenha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_tipo_login", referencedColumnName = "co_tipo_login")
    private TipoLogin tipoLogin;

    @Column(name = "dh_criacao")
    @Nullable
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao")
    @Nullable
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

}
