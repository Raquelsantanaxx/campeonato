package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.entity.Jogador;

import java.util.List;
import java.util.Optional;

public interface JogadorService {

    Jogador salvar(Jogador jogador );

    List<Jogador> listarTodos ();

    Optional<Jogador> buscarPorId (Integer id);

    List<Jogador> listarPorEquipe (Integer idEquipe);

    void deletar(Integer id);
}
