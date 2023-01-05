package com.terraco.terracoDaCida.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name="login")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @Id
    @Column(name = "co_login")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coLogin;

    @Column(name = "no_usuario", nullable = false)
    private String noUsuario;

    @Column(name = "co_senha", nullable = false)
    private String coSenha;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_tipo_login", referencedColumnName = "co_tipo_login")
    @JsonDeserialize
    private TipoLogin tipoLogin;

}
