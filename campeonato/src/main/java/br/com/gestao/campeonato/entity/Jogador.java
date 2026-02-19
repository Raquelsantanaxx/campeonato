package br.com.gestao.campeonato.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jogador")
@Getter
@Setter
@NoArgsConstructor
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jogador")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;

    @Column(name = "nome",nullable = false, length = 100)
    private String nome;

    @Column(name = "posicao",nullable = false, length = 100)
    private String posicao;

    @Column(name = "numero_camisa", nullable = false)
    private Integer numeroCamisa;
}
