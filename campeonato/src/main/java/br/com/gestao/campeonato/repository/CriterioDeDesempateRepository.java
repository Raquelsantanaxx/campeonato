package br.com.gestao.campeonato.repository;

import br.com.gestao.campeonato.entity.CriterioDeDesempate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriterioDeDesempateRepository extends JpaRepository<CriterioDeDesempate, Integer>{

        List<CriterioDeDesempate> findByCampeonatoId(Integer campeonatoId);

}
