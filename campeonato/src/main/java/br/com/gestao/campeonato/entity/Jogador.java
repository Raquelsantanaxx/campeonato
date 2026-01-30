package br.com.gestao.campeonato.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "jogador ")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jogador")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Jogador equipe;

    @Column(name = "nome",nullable = false, length = 100)
    private String nome;

    @Column(name = "posicao",nullable = false, length = 100)
    private String posicao;

    @Column(name = "numero_camisa", nullable = false)
    private Integer numeroCamisa;
}
