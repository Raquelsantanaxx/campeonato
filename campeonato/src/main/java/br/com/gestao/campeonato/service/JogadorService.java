package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.entity.Jogador;

import java.util.List;

public interface JogadorService {

    Jogador salvar(Jogador jogador, String emailUsuario);

    List<Jogador> listarTodos();

    Jogador buscarPorId(Integer id);

    List<Jogador> listarPorEquipe(Integer idEquipe);

    void deletar(Integer id, String emailUsuario);
}
