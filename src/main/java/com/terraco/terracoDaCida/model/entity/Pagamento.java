package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="pagamento")
@Data
public class Pagamento {
    @Id
    @Column(name="co_pagamento")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coPagamento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="co_comanda", nullable = false)
    private Comanda coComanda;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="co_tipo_pagamento", referencedColumnName = "co_tipo_pagamento", nullable = false)
    private TipoPagamento tipoPagamento;

    @Column(name="vr_pagamento", nullable = false)
    private BigDecimal vrPagamento;

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
