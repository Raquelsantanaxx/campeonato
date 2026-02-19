package br.com.gestao.campeonato.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name= "equipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_campeonato")
    private Campeonato campeonato;

    @Column(name = "nome",nullable = false, length = 100)
    private String nome;

    @Column(name= "sigla",nullable = false, length = 50)
    private String sigla;

    @Column (name = "cor_primaria")
    private String corPrimaria;

    @Column (name = "cor_secundaria")
    private String corSecundaria;

}
