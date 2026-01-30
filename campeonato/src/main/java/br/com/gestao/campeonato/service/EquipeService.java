package br.com.gestao.campeonato.service;
import br.com.gestao.campeonato.entity.Equipe;
import java.util.List;
import java.util.Optional;

public interface EquipeService {
    Equipe salvar(Equipe  equipe);

    Optional<Equipe> buscarPorId(Integer id);

    List<Equipe> listarTodos();

    void deletar(Integer id);
}

