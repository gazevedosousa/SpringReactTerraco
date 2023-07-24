package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="produto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @Column
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String noProduto;

    @Column(nullable = false)
    private BigDecimal vrProduto;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private TipoProduto tipoProduto;

    @Column(nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataExclusao;

    @OneToMany(mappedBy = "produto")
    private List<ComandaProduto> comandaProdutos;

}
