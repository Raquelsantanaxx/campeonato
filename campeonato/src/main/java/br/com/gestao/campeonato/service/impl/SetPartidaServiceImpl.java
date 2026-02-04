package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.entity.SetPartida;
import br.com.gestao.campeonato.repository.PartidaRepository;
import br.com.gestao.campeonato.repository.SetPartidaRepository;
import br.com.gestao.campeonato.service.SetPartidaService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SetPartidaServiceImpl implements SetPartidaService {

    private final SetPartidaRepository setRepository;
    private final PartidaRepository partidaRepository;

    public SetPartidaServiceImpl(SetPartidaRepository setRepository,
                                 PartidaRepository partidaRepository) {
        this.setRepository = setRepository;
        this.partidaRepository = partidaRepository;
    }

    @Override
    public SetPartida criarSet(Integer partidaId, SetPartida set) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(()->
                        new RuntimeException ("Partida não encontrada"));

        set.setPartida(partida);

        return setRepository.save(set);
    }


    @Override
    public List<SetPartida> listarSets(Integer partidaId) {
        return setRepository.findByPartidaId(partidaId);
    }
}
