package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="produto")
@Data
public class Produto {
    @Id
    @Column(name="co_produto")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coProduto;

    @Column(name="no_produto", unique = true, nullable = false)
    private String noProduto;

    @Column(name="vr_produto", nullable = false)
    private BigDecimal vrProduto;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_tipo_produto", referencedColumnName = "co_tipo_produto", nullable = false)
    private TipoProduto tipoProduto;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

    @ManyToMany
    @JoinColumn(name="co_produto")
    private List<ComandaProduto> comandaProdutos;

}
