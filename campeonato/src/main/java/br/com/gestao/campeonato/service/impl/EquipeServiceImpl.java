package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.repository.EquipeRepository;
import br.com.gestao.campeonato.service.EquipeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EquipeServiceImpl implements EquipeService {

    private final EquipeRepository equipeRepository;

    public EquipeServiceImpl(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    @Override
    public Equipe salvar(Equipe equipe) {

        // REGRA 1: equipe precisa estar vinculada a um campeonato
        if (equipe.getCampeonato() == null || equipe.getCampeonato().getId() == null) {
            throw new RuntimeException("A equipe deve estar vinculada a um campeonato.");
        }

        Integer campeonatoId = equipe.getCampeonato().getId();

        // REGRA 2: nome não pode repetir no mesmo campeonato
        if (equipeRepository.existsByCampeonatoIdAndNomeIgnoreCase(campeonatoId, equipe.getNome())) {
            throw new RuntimeException("Já existe uma equipe com esse nome neste campeonato.");
        }

        // REGRA 3: sigla não pode repetir no mesmo campeonato
        if (equipeRepository.existsByCampeonatoIdAndSiglaIgnoreCase(campeonatoId, equipe.getSigla())) {
            throw new RuntimeException("Já existe uma equipe com essa sigla neste campeonato.");
        }

        return equipeRepository.save(equipe);
    }

    @Override
    public Optional<Equipe> buscarPorId(Integer id) {
        return equipeRepository.findById(id);
    }

    @Override
    public List<Equipe> listarTodos() {
        return equipeRepository.findAll();
    }

    @Override
    public void deletar(Integer id) {
        equipeRepository.deleteById(id);
    }
    @Override
    public List<Equipe> listarPorCampeonato(Integer campeonatoId) {
        return equipeRepository.findByCampeonatoId(campeonatoId);
    }
    
}

