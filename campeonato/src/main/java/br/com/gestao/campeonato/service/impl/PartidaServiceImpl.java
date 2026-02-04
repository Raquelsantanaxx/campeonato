package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Partida;
import br.com.gestao.campeonato.repository.PartidaRepository;
import br.com.gestao.campeonato.service.PartidaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PartidaServiceImpl implements PartidaService {

    private final PartidaRepository partidaRepository;

    public PartidaServiceImpl(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    @Override
    public Partida salvar(Partida partida) {

        if (partida.getDataHora() == null) {
            partida.setDataHora(LocalDateTime.now());
        }

        return partidaRepository.save(partida);
    }


    @Override
    public Optional<Partida> buscarPorId(Integer id) {
        return partidaRepository.findById(id);
    }

    @Override
    public List<Partida> listarTodos() {
        return partidaRepository.findAll();
    }

    @Override
    public void deletar(Integer id) {
        partidaRepository.deleteById(id);
    }

    @Override
    public List<Partida> buscarPorCampeonato(Integer idCampeonato) {
        return partidaRepository.findByCampeonatoId(idCampeonato);
    }

    @Override
    public List<Partida> buscarPorEquipe(Integer idEquipe) {
        return partidaRepository.findByEquipeMandante_IdOrEquipeVisitante_Id(idEquipe, idEquipe);
    }
}

