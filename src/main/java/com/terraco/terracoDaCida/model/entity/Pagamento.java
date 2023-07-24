package com.terraco.terracoDaCida.model.entity;

import com.terraco.terracoDaCida.model.enums.TipoPagamentoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="pagamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    @Id
    @Column
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Comanda comanda;

    @Column(nullable = false)
    private TipoPagamentoEnum tipoPagamento;

    @Column(nullable = false)
    private BigDecimal vrPagamento;

    @Column(nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime dataExclusao;

}
