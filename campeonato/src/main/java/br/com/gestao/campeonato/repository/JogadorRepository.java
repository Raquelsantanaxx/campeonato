package br.com.gestao.campeonato.repository;
import br.com.gestao.campeonato.entity.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogadorRepository extends JpaRepository<Jogador,Integer> {

    List<Jogador> findByEquipeId(Integer idEquipe);

}
