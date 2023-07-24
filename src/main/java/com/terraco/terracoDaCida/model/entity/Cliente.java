package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="cliente")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @Column
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String noCliente;

    @Column(nullable = false)
    private String celCliente;

    @Column(nullable = true)
    private String emailCliente;

    @Column(nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataExclusao;

    @OneToMany(mappedBy = "cliente")
    private List<Comanda> comandas;

}
