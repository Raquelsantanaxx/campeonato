package br.com.gestao.campeonato.service.impl;

import br.com.gestao.campeonato.entity.Jogador;
import br.com.gestao.campeonato.repository.JogadorRepository;
import br.com.gestao.campeonato.service.JogadorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogadorServiceImpl implements JogadorService {


    private final JogadorRepository jogadorRepository;

    public JogadorServiceImpl(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    @Override
    public Jogador salvar(Jogador jogador) {
        return jogadorRepository.save(jogador);
    }


    @Override
    public List<Jogador> listarTodos() {
        return jogadorRepository.findAll();
    }

    @Override
    public Optional<Jogador> buscarPorId(Integer id) {
        return jogadorRepository.findById(id);
    }

    @Override
    public List<Jogador> listarPorEquipe(Integer idEquipe) {
        return jogadorRepository.findAll();
    }


    @Override
    public void deletar(Integer id) {
        jogadorRepository.deleteById(id);

    }
}
