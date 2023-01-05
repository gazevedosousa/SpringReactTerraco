package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name="situacaocomanda")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoComanda {
    @Id
    @Column(name="co_situacao_comanda")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coSituacaoComanda;

    @Column(name="no_situacao_comanda", nullable = false)
    private String noSituacaoComanda;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

    @OneToOne(mappedBy = "situacaoComanda")
    private Comanda comanda;

}
