package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.repository.CampeonatoRepository;
import br.com.gestao.campeonato.repository.EquipeRepository;
import br.com.gestao.campeonato.repository.PartidaRepository;
import br.com.gestao.campeonato.service.EquipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipeServiceImpl implements EquipeService {

    private final EquipeRepository equipeRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final PartidaRepository partidaRepository;

    public EquipeServiceImpl(EquipeRepository equipeRepository,
                             CampeonatoRepository campeonatoRepository,
                             PartidaRepository partidaRepository) {
        this.equipeRepository = equipeRepository;
        this.campeonatoRepository = campeonatoRepository;
        this.partidaRepository = partidaRepository;
    }

    @Override
    public Equipe salvar(Equipe equipe, String emailUsuario) {

        if (equipe.getCampeonato() == null ||
                equipe.getCampeonato().getId() == null) {

            throw new RuntimeException("Campeonato obrigatório.");
        }

        Integer campeonatoId = equipe.getCampeonato().getId();

        Campeonato campeonato = campeonatoRepository
                .findById(campeonatoId)
                .orElseThrow(() ->
                        new RuntimeException("Campeonato não encontrado."));

        if (!campeonato.getOrganizador()
                .getEmail()
                .equals(emailUsuario)) {

            throw new RuntimeException(
                    "Você não pode cadastrar equipe neste campeonato.");
        }

        equipe.setCampeonato(campeonato);

        return equipeRepository.save(equipe);
    }

    @Override
    public Equipe buscarPorId(Integer id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));
    }

    @Override
    public List<Equipe> listarTodos() {
        return equipeRepository.findAll();
    }

    @Override
    @Transactional
    public void deletar(Integer id, String emailUsuario) {

        Equipe equipe = buscarPorId(id);

        // 🔐 validar organizador
        if (!equipe.getCampeonato()
                .getOrganizador()
                .getEmail()
                .equals(emailUsuario)) {

            throw new RuntimeException("Você não pode excluir esta equipe.");
        }

        // 🔎 verificar se possui partidas vinculadas
        boolean possuiPartidas =
                partidaRepository.existsByEquipeMandanteId(id)
                        || partidaRepository.existsByEquipeVisitanteId(id);

        if (possuiPartidas) {
            throw new RuntimeException(
                    "Não é possível excluir a equipe pois ela já possui partidas."
            );
        }

        equipeRepository.delete(equipe);
    }

    @Override
    public List<Equipe> listarPorCampeonato(Integer campeonatoId) {
        return equipeRepository.findByCampeonatoId(campeonatoId);
    }
}

