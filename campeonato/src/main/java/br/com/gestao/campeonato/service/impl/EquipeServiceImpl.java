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
}
