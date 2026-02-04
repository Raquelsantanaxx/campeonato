package br.com.gestao.campeonato.service;
import br.com.gestao.campeonato.entity.SetPartida;

import java.util.List;

public interface SetPartidaService {

    SetPartida criarSet(Integer partidaId, SetPartida set);

    List<SetPartida> listarSets(Integer partidaId);

}
