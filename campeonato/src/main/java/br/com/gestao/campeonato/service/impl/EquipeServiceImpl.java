package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Campeonato;
import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.repository.CampeonatoRepository;
import br.com.gestao.campeonato.repository.EquipeRepository;
import br.com.gestao.campeonato.service.EquipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipeServiceImpl implements EquipeService {

    private final EquipeRepository equipeRepository;
    private final CampeonatoRepository campeonatoRepository;

    public EquipeServiceImpl(EquipeRepository equipeRepository,
                             CampeonatoRepository campeonatoRepository) {
        this.equipeRepository = equipeRepository;
        this.campeonatoRepository = campeonatoRepository;

    }

    @Override
    public Equipe salvar(Equipe equipe, String emailUsuario) {

        if (equipe.getCampeonato() == null ||
                equipe.getCampeonato().getId() == null) {

            throw new RuntimeException("Campeonato obrigatório.");
        }

        Integer campeonatoId = equipe.getCampeonato().getId();

        // 🔥 BUSCAR CAMPEONATO REAL DO BANCO
        Campeonato campeonato = campeonatoRepository
                .findById(campeonatoId)
                .orElseThrow(() ->
                        new RuntimeException("Campeonato não encontrado."));

        // 🔐 VERIFICAR SE É O DONO
        if (!campeonato.getOrganizador()
                .getEmail()
                .equals(emailUsuario)) {

            throw new RuntimeException(
                    "Você não pode cadastrar equipe neste campeonato.");
        }

        // 🔥 AGORA SIM ASSOCIAR O OBJETO REAL
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
    public void deletar(Integer id, String emailUsuario) {

        Equipe equipe = buscarPorId(id);

        if (!equipe.getCampeonato()
                .getOrganizador()
                .getEmail()
                .equals(emailUsuario)) {

            throw new RuntimeException("Você não pode excluir esta equipe.");
        }

        equipeRepository.delete(equipe);
    }

    @Override
    public List<Equipe> listarPorCampeonato(Integer campeonatoId) {
        return equipeRepository.findByCampeonatoId(campeonatoId);
    }
}

