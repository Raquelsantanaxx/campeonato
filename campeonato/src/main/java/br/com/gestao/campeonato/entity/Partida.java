package br.com.gestao.campeonato.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "partida")
@Getter
@Setter
@NoArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partida")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_campeonato", nullable = false)
    private Campeonato campeonato;


    @ManyToOne
    @JoinColumn(name = "id_equipe_mandante", nullable = false)
    private Equipe equipeMandante;

    @ManyToOne
    @JoinColumn(name = "id_equipe_visitante", nullable = false)
    private Equipe equipeVisitante;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(length = 150)
    private String local;

    @Column(name = "resultado_final", length = 10)
    private String resultadoFinal;

    @Column(nullable = false)
    private Boolean finalizada = false;

}
