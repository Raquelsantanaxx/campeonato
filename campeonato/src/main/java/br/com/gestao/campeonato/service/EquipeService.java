package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.entity.Equipe;
import java.util.List;

public interface EquipeService {

    Equipe salvar(Equipe equipe, String emailUsuario);

    Equipe buscarPorId(Integer id);

    List<Equipe> listarTodos();

    void deletar(Integer id, String emailUsuario);

    List<Equipe> listarPorCampeonato(Integer campeonatoId);
}

