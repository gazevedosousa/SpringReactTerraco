package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name="situacaocomanda")
@Data
@RequiredArgsConstructor
@Builder
public class SituacaoComanda {
    @Id
    @Column(name="co_situacao_comanda")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coSituacaoComanda;

    @Column(name="no_situacao_comanda")
    private String noSituacaoComanda;

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

    @OneToOne(mappedBy = "situacaoComanda")
    private Comanda comanda;

}
