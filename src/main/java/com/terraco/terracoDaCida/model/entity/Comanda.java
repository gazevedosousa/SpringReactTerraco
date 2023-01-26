package com.terraco.terracoDaCida.model.entity;

import com.terraco.terracoDaCida.model.enums.SituacaoComandaEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="comanda")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comanda {
    @Id
    @Column
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private SituacaoComandaEnum situacaoComanda;

    @Column(nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataExclusao;

    @OneToMany(mappedBy = "comanda")
    private List<ComandaProduto> comandaProdutos;

}
