package com.terraco.terracoDaCida.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="comanda")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comanda {
    @Id
    @Column(name = "co_comanda")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long coComanda;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_cliente", referencedColumnName = "co_cliente", nullable = false)
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "co_situacao_comanda", referencedColumnName = "co_situacao_comanda", nullable = false)
    private SituacaoComanda situacaoComanda;

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
    @JoinColumn(name="co_comanda")
    private List<ComandaProduto> comandaProdutos;

    @OneToMany
    @JoinColumn(name="co_comanda")
    private List<Pagamento> pagamentos;


}
