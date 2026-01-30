package br.com.gestao.campeonato.repository;

import br.com.gestao.campeonato.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EquipeRepository extends JpaRepository<Equipe,Integer> {
}
