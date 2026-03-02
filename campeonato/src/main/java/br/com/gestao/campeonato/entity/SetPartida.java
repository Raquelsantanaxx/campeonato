package br.com.gestao.campeonato.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "set_partida",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"id_partida", "numero_set"}
        )
)
@Getter
@Setter
@NoArgsConstructor

public class SetPartida {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_set")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_partida", nullable = false)
    private Partida partida;

    @Column(name = "numero_set", nullable = false)
    private Integer numeroSet;

    @Column(name = "pontos_mandante", nullable = false)
    private Integer pontosMandante;

    @Column (name = "pontos_visitante", nullable = false)
    private Integer pontosVisitante;

}
