package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name="comandaproduto")
@Data
@RequiredArgsConstructor
@Builder
public class ComandaProduto {
    @Id
    @Column(name = "co_comanda_produto")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coComandaProduto;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "co_comanda")
    private Comanda comanda;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "co_produto")
    private Produto produto;

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
