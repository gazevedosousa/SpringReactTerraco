package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
@Entity
@Table(name="tipologin")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoLogin {
    @Id
    @Column(name="co_tipo_login")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coTipoLogin;

    @Column(name="no_tipo_login", nullable = false)
    private String noTipoLogin;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

    @OneToOne(mappedBy = "tipoLogin")
    private Login login;

}
