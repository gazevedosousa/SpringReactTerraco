package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name="cliente")
@Data
@RequiredArgsConstructor
@Builder
public class Cliente {
    @Id
    @Column(name = "co_cliente")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coCliente;

    @Column(name = "no_cliente")
    private String noCliente;

    @Column(name = "cel_cliente")
    private String celCliente;

    @Column(name = "email_cliente")
    private String emailCliente;

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

    @OneToMany(mappedBy = "cliente")
    private Comanda comanda;

}
