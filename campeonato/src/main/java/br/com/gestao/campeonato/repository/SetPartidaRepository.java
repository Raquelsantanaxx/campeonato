package br.com.gestao.campeonato.repository;

import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.entity.SetPartida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetPartidaRepository extends JpaRepository<SetPartida, Integer> {

    List<SetPartida> findByPartidaId(Integer partidaId);
}

