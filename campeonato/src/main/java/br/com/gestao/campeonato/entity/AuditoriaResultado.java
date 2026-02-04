package br.com.gestao.campeonato.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_resultado")
public class AuditoriaResultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_partida", nullable = false)
    private Partida partida;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column (name = "data_alteracao", nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false, length = 255)
    private String justificativa;

    @Column(name = "dados_anteriores", columnDefinition = "TEXT")
    private String dadosAnteriores;


}
