package br.com.gestao.campeonato.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "criterio_desempate")
@Getter
@Setter
@NoArgsConstructor
public class CriterioDeDesempate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_criterio")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_campeonato", nullable = false)
    private Campeonato campeonato;

    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    @Column(name = "criterio",nullable = false, length = 100)
    private String criterio;

}
