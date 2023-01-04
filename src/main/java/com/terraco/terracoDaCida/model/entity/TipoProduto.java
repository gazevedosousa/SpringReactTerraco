package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
@Entity
@Table(name="tipoproduto")
@Data
public class TipoProduto {
    @Id
    @Column(name="co_tipo_produto")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coTipoProduto;

    @Column(name="no_tipo_produto", nullable = false)
    private String noTipoProduto;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

    @OneToOne(mappedBy = "tipoProduto")
    private Produto produto;

}
