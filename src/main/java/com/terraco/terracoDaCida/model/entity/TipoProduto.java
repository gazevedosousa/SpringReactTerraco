package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
@Entity
@Table(name="tipoproduto")
@Data
@RequiredArgsConstructor
@Builder
public class TipoProduto {
    @Id
    @Column(name="co_tipo_produto")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coTipoProduto;

    @Column(name="no_tipo_produto")
    private String noTipoProduto;

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

    @OneToOne(mappedBy = "tipoProduto")
    private Produto produto;

}
