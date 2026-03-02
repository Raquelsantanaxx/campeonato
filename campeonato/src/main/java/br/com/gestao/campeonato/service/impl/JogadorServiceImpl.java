package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Equipe;
import br.com.gestao.campeonato.entity.Jogador;
import br.com.gestao.campeonato.repository.EquipeRepository;
import br.com.gestao.campeonato.repository.JogadorRepository;
import br.com.gestao.campeonato.service.JogadorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogadorServiceImpl implements JogadorService {

    private final JogadorRepository jogadorRepository;
    private final EquipeRepository equipeRepository;

    public JogadorServiceImpl(JogadorRepository jogadorRepository,
                              EquipeRepository equipeRepository) {
        this.jogadorRepository = jogadorRepository;
        this.equipeRepository = equipeRepository;
    }

    // ==========================================
    // SALVAR
    // ==========================================
    @Override
    public Jogador salvar(Jogador jogador, String emailUsuario) {

        if (jogador.getEquipe() == null ||
                jogador.getEquipe().getId() == null) {
            throw new RuntimeException("Jogador deve estar vinculado a uma equipe.");
        }

        Equipe equipe = equipeRepository
                .findById(jogador.getEquipe().getId())
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));

        // 🔒 valida dono do campeonato
        if (!equipe.getCampeonato()
                .getOrganizador()
                .getEmail()
                .equals(emailUsuario)) {

            throw new RuntimeException("Você não pode alterar jogadores desta equipe.");
        }

        jogador.setEquipe(equipe);

        return jogadorRepository.save(jogador);
    }

    // ==========================================
    // BUSCAR POR ID
    // ==========================================
    @Override
    public Jogador buscarPorId(Integer id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado."));
    }

    // ==========================================
    // LISTAR TODOS  ✅ (ERA O QUE FALTAVA)
    // ==========================================
    @Override
    public List<Jogador> listarTodos() {
        return jogadorRepository.findAll();
    }

    // ==========================================
    // LISTAR POR EQUIPE
    // ==========================================
    @Override
    public List<Jogador> listarPorEquipe(Integer idEquipe) {
        return jogadorRepository.findByEquipeId(idEquipe);
    }

    // ==========================================
    // DELETAR
    // ==========================================
    @Override
    public void deletar(Integer id, String emailUsuario) {

        Jogador jogador = buscarPorId(id);

        if (!jogador.getEquipe()
                .getCampeonato()
                .getOrganizador()
                .getEmail()
                .equals(emailUsuario)) {

            throw new RuntimeException("Você não pode excluir este jogador.");
        }

        jogadorRepository.delete(jogador);
    }
}

