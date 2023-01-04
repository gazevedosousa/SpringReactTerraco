package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="cliente")
@Data
public class Cliente {
    @Id
    @Column(name = "co_cliente")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int coCliente;

    @Column(name = "no_cliente", nullable = false)
    private String noCliente;

    @Column(name = "cel_cliente", nullable = false)
    private String celCliente;

    @Column(name = "email_cliente", nullable = true)
    private String emailCliente;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

    @OneToMany
    @JoinColumn(name="co_cliente")
    private List<Comanda> comandas;

}
