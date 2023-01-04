package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
@Entity
@Table(name="tipologin")
@Data
@RequiredArgsConstructor
@Builder
public class TipoLogin {
    @Id
    @Column(name="co_tipo_login")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coTipoLogin;

    @Column(name="no_tipo_login")
    private String noTipoLogin;

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

    @OneToOne(mappedBy = "tipoLogin")
    private Login login;

}
