package br.com.gestao.campeonato.service;

import br.com.gestao.campeonato.entity.Partida;
import java.util.List;
import java.util.Optional;

public interface PartidaService {

    Partida salvar(Partida partida);

    Partida buscarPorId(Integer id);

    List<Partida> listarTodos();

    void deletar(Integer id);

    List<Partida> buscarPorCampeonato(Integer idCampeonato);

    List<Partida> buscarPorEquipe(Integer idEquipe);

    Partida atualizarResultadoManual(Integer partidaId,
                                     String novoResultado,
                                     Integer usuarioId);
}

