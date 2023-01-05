package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="comandaproduto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandaProduto {
    @Id
    @Column(name = "co_comanda_produto")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coComandaProduto;

    @ManyToMany
    @JoinColumn(name = "co_comanda", nullable = false)
    private List<Comanda> comandas;

    @ManyToMany
    @JoinColumn(name = "co_produto", nullable = false)
    private List<Produto> produtos;

    @Column(name = "dh_criacao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhCriacao;

    @Column(name = "dh_atualizacao", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhAtualizacao;

    @Column(name = "dh_exclusao", nullable = true)
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dhExclusao;

}
