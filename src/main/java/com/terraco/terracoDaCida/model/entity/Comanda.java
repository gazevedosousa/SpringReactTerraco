package com.terraco.terracoDaCida.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="comanda")
@Data
@RequiredArgsConstructor
@Builder
public class Comanda {
    @Id
    @Column(name = "co_comanda")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coComanda;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_cliente", referencedColumnName = "co_cliente")
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_situacao_comanda", referencedColumnName = "co_situacao_comanda")
    private SituacaoComanda situacaoComanda;

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

    @OneToMany(mappedBy = "comanda")
    Set<ComandaProduto> comandaProdutos;


}
