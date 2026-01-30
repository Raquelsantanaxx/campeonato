package br.com.gestao.campeonato.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campeonato")
public class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campeonato")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_organizador")
    private Usuario organizador;


    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 50)
    private String formato;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(nullable = false)
    private Boolean ativo = true;
}
