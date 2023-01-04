package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name="login")
@Data
public class Login {
    @Id
    @Column(name = "co_login")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int coLogin;

    @Column(name = "no_usuario", unique = true, nullable = false)
    private String noUsuario;

    @Column(name = "co_senha", nullable = false)
    private String coSenha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_tipo_login", referencedColumnName = "co_tipo_login")
    private TipoLogin tipoLogin;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

}
