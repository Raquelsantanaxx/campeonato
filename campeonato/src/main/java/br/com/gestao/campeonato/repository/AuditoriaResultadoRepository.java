package br.com.gestao.campeonato.repository;

import br.com.gestao.campeonato.entity.AuditoriaResultado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditoriaResultadoRepository extends JpaRepository<AuditoriaResultado, Integer > {

    List<AuditoriaResultado> findByPartidaId(Integer partidaId);

}
