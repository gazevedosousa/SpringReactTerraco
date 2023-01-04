package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="pagamento")
@Data
@RequiredArgsConstructor
@Builder
public class Pagamento {
    @Id
    @Column(name="co_pagamento")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coPagamento;

    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="co_comanda")
    private Comanda coComanda;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="co_tipo_pagamento", referencedColumnName = "co_tipo_pagamento")
    private TipoPagamento tipoPagamento;

    @Column(name="vr_produto")
    private BigDecimal vrProduto;

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
