package br.com.gestao.campeonato.repository;

import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.entity.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipeRepository extends JpaRepository<Equipe,Integer> {

    List<Equipe> findByCampeonatoId(Integer campeonatoId);

}
