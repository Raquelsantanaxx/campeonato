package br.com.gestao.campeonato.repository;

import br.com.gestao.campeonato.entity.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {

    List<Partida> findByCampeonatoId(Integer idCampeonato);

    List<Partida> findByEquipeMandante_IdOrEquipeVisitante_Id(Integer mandanteId, Integer visitanteId);
    boolean existsByEquipeMandante_IdOrEquipeVisitante_Id(
            Integer mandanteId,
            Integer visitanteId);

}
