package br.com.gestao.campeonato.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_usuario")
    private Integer id;

    @Column (name="nome", nullable = false, length= 150)
    private String nome;

    @Column (name="email", nullable = false, unique = true ,length= 150)
    private String email;

    @Column (name="senha_hash", nullable = false, length= 150)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(name= "perfil", nullable = false)
    private PerfilUsuario perfil;
}
