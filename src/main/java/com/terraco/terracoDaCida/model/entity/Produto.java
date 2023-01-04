package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="produto")
@Data
@RequiredArgsConstructor
@Builder
public class Produto {
    @Id
    @Column(name="co_produto")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coProduto;

    @Column(name="no_produto", unique = true)
    private String noProduto;

    @Column(name="vr_produto")
    private BigDecimal vrProduto;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_tipo_produto", referencedColumnName = "co_tipo_produto")
    private TipoProduto tipoProduto;

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

    @OneToMany(mappedBy = "produto")
    Set<ComandaProduto> comandaProdutos;

}
