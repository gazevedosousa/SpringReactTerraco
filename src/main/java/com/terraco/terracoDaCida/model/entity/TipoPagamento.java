package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
@Entity
@Table(name="tipopagamento")
@Data
public class TipoPagamento {
    @Id
    @Column(name="co_tipo_pagamento")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coTipoPagamento;

    @Column(name="no_tipo_pagamento", nullable = false)
    private String noTipoPagamento;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

    @OneToOne(mappedBy = "tipoPagamento")
    private Pagamento pagamento;

}
